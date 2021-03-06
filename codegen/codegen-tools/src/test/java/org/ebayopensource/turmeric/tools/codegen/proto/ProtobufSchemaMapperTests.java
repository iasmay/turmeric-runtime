/**
 * 
 */
package org.ebayopensource.turmeric.tools.codegen.proto;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.ebayopensource.turmeric.tools.TestResourceUtil;
import org.ebayopensource.turmeric.tools.codegen.AbstractServiceGeneratorTestCase;
import org.ebayopensource.turmeric.tools.codegen.CodeGenContext;
import org.ebayopensource.turmeric.tools.codegen.exception.CodeGenFailedException;
import org.ebayopensource.turmeric.tools.codegen.external.wsdl.parser.WSDLParserException;
import org.ebayopensource.turmeric.tools.codegen.external.wsdl.parser.schema.SchemaType;
import org.ebayopensource.turmeric.tools.codegen.fastserformat.FastSerFormatCodegenBuilder;
import org.ebayopensource.turmeric.tools.codegen.fastserformat.protobuf.ProtobufSchemaMapper;
import org.ebayopensource.turmeric.tools.codegen.fastserformat.protobuf.model.ProtobufEnumMessage;
import org.ebayopensource.turmeric.tools.codegen.fastserformat.protobuf.model.ProtobufField;
import org.ebayopensource.turmeric.tools.codegen.fastserformat.protobuf.model.ProtobufMessage;
import org.ebayopensource.turmeric.tools.codegen.fastserformat.protobuf.model.ProtobufOption;
import org.ebayopensource.turmeric.tools.codegen.fastserformat.protobuf.model.ProtobufOptionType;
import org.ebayopensource.turmeric.tools.codegen.fastserformat.protobuf.model.ProtobufSchema;
import org.ebayopensource.turmeric.tools.codegen.fastserformat.protobuf.model.SchemaTypeName;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author rkulandaivel
 * 
 */
public class ProtobufSchemaMapperTests extends AbstractServiceGeneratorTestCase {

	

	public  File getProtobufInput(String name) {
		return TestResourceUtil.getResource("org/ebayopensource/turmeric/test/tools/codegen/data/proto/"
				+ name);
	}
	

	@Ignore
	@Test

	public void testDePolymorphizedFindItemServiceWsdl() throws Exception {
		
		File destDir = testingdir.getDir();
		File binDir = new File(destDir,"bin");
		File wsdl = getProtobufInput("FindItemServiceAdjustedV3.wsdl");	
		
		String testArgs[] = new String[] {
				"-servicename",
				"FindItemService",
				"-wsdl",wsdl.getAbsolutePath(),
				
				"-genType", "ClientNoConfig", "-src", destDir.getAbsolutePath(),
				"-dest", destDir.getAbsolutePath(), "-scv", "1.0.0", "-bin",binDir.getAbsolutePath(),
				// "-enabledNamespaceFolding",
				"-nonXSDFormats", "protobuf" };
		
		
		CodeGenContext context = ProtobufSchemaMapperTestUtils.getCodeGenContext(testArgs);
		
		FastSerFormatCodegenBuilder.getInstance().validateServiceIfApplicable(context);
		
		List<SchemaType> listOfSchemaTypes;
		try {
			listOfSchemaTypes = FastSerFormatCodegenBuilder.getInstance().generateSchema( context );
		} catch (WSDLParserException e) {
			throw new CodeGenFailedException( "Generate Schema Failed.", e );
		}
		
		int i = 0;
		for(SchemaType schemaType : listOfSchemaTypes){
			//System.out.println(i+"======"+ schemaType.getTypeName() + "===" + schemaType.getClass().getName());
			i++;
		}
		ProtobufSchema schema = ProtobufSchemaMapper.getInstance().createProtobufSchema(listOfSchemaTypes, context);
		//System.out.println(schema);
		String dotprotofilepath = getProtobufInput("FindItemServiceAdjustedV3.proto").getAbsolutePath();

		List<ProtobufMessage> messagesFromFile = ProtobufSchemaMapperTestUtils.loadFindItemServiceManuallyWrittenProtoFile( dotprotofilepath );
		updateMessagesLoadedFromFile( messagesFromFile, context, "com.ebay.marketplace.search.v1.services" );
		System.out.println( "messagesFromFile==" +messagesFromFile );
		
		validateTheSchema(schema, context, messagesFromFile);
		ProtobufSchemaMapperTestUtils.validateTagNumberGeneration( context, schema );
	}

	private void updateMessagesLoadedFromFile(List<ProtobufMessage> messagesFromFile, CodeGenContext context, String basePackage ){
		for( ProtobufMessage message : messagesFromFile ){
			String msgName = message.getMessageName();
			String typeName = msgName;
			if( message.isEnumType() ){
				typeName = ((ProtobufEnumMessage)message).getEnumMessageName();
			}
			message.setJaxbClassName(basePackage + "." + msgName);
			message.setEprotoClassName(basePackage + ".proto.extended.E" + msgName);
			
			if( message.isEnumType() ){
				message.setJprotoClassName(basePackage + ".proto.FindItemService$" + typeName + "$"+msgName);
			}else{
				message.setJprotoClassName(basePackage + ".proto.FindItemService$" + typeName);
			}
			for(ProtobufField field : message.getFields()){
				field.setTypeOfField( ProtobufSchemaMapperTestUtils.getFieldType(field) );
			}

			
			String serviceNamespace = context.getNamespace();


			//Test type FieldValue
			QName fieldValName = new QName(serviceNamespace, msgName);
			SchemaTypeName fieldValueTypeName = new SchemaTypeName( fieldValName );
			message.setSchemaTypeName(fieldValueTypeName);
		}
	}
	
	private void validateTheSchema(ProtobufSchema schema, CodeGenContext context, List<ProtobufMessage> messagesFromFile) throws Exception{
		Map<SchemaTypeName, ProtobufMessage> schemaTypeMap = ProtobufSchemaMapperTestUtils.createMessageMapFromList( schema.getMessages() );

		Map<SchemaTypeName, ProtobufMessage> newSchemaTypeMap = new HashMap<SchemaTypeName, ProtobufMessage>();
		//re map
		for( Map.Entry<SchemaTypeName, ProtobufMessage> entry : schemaTypeMap.entrySet() ){
			SchemaTypeName key = entry.getKey();
			QName oldQ = key.getTypeName();
			QName newQ = new QName( oldQ.getNamespaceURI(), entry.getValue().getMessageName() );

			SchemaTypeName newKey = new SchemaTypeName( newQ );
			newSchemaTypeMap.put(newKey, entry.getValue());
		}
		
		for( ProtobufMessage message : messagesFromFile ){
			ProtobufMessage messageFromModel = newSchemaTypeMap.get(message.getSchemaTypeName());
			if(messageFromModel == null){
				throw new Exception("The model does not have an message corresponding to name " + message.getSchemaTypeName());
			}
			boolean equal = false;
			if( message instanceof ProtobufEnumMessage){
				equal = ProtobufSchemaMapperTestUtils.ProtobufMessageComparator.compareEnumMessage((ProtobufEnumMessage)message, (ProtobufEnumMessage)messageFromModel);
			}else{
				equal = ProtobufSchemaMapperTestUtils.ProtobufMessageComparator.compareMessage(message, messageFromModel);				
			}

			if(!equal){
				System.out.println(message);
				System.out.println(messageFromModel);
				throw new Exception("The proto buf message generated for " + message.getSchemaTypeName() + " has some issues.");
			}
			if("FindItemsResponse".equals(messageFromModel.getMessageName())){
				if( !messageFromModel.isRootType() ){
					throw new Exception("The proto buf message FindItemsResponse should be a root type" );
				}
			}
		}

		if( !schema.getDotprotoFileName().equals( context.getServiceAdminName() + ".proto" )){
			Assert.fail("Dot proto file name is wrong");
		}

		if( !schema.getDotprotoFilePackage().equals("com.ebay.marketplace.search.v1.services") ){
			Assert.fail("Dot proto file package is wrong");
		}

		if( ! (schema.getMessagesImported().size() == 0 )){
			Assert.fail( "Message imports not supposed to be configured" );
		}
		ProtobufOption option1 = new ProtobufOption();
		option1.setOptionType( ProtobufOptionType.JAVA_OUTER_CLASS_NAME );
		option1.setOptionValue( context.getServiceAdminName() );
		
		ProtobufOption option2 = new ProtobufOption();
		option2.setOptionType( ProtobufOptionType.JAVA_PACKAGE_NAME );
		option2.setOptionValue( "com.ebay.marketplace.search.v1.services.proto" );

		ProtobufOption option3 = new ProtobufOption();
		option3.setOptionType( ProtobufOptionType.OPTIMIZE_FOR );
		option3.setOptionValue( "SPEED" );
		for( ProtobufOption opt : schema.getDotprotoOptions() ){
			if(opt.getOptionType() == ProtobufOptionType.JAVA_OUTER_CLASS_NAME ){
				if( !context.getServiceAdminName().equals(opt.getOptionValue() )  ){
					Assert.fail("The protobuf option value is configured wrong for  JAVA_OUTER_CLASS_NAME");
				}
			}else if( opt.getOptionType() == ProtobufOptionType.JAVA_PACKAGE_NAME ){
				if( !"com.ebay.marketplace.search.v1.services.proto".equals(opt.getOptionValue() )  ){
					Assert.fail("The protobuf option value is configured wrong for  JAVA_PACKAGE_NAME");
				}				
			}else if( opt.getOptionType() == ProtobufOptionType.OPTIMIZE_FOR ){
				if( !"SPEED".equals(opt.getOptionValue() )  ){
					Assert.fail("The protobuf option value is configured wrong for  OPTIMIZE_FOR");
				}
			}else {
				Assert.fail("The protobuf options are configured wrong");
			}
		}

		
		Assert.assertTrue(schema.toString(), true);
	}
}
