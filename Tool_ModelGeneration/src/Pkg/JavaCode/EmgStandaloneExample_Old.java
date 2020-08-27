package Pkg.JavaCode;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emg.EmgModule;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.IEolExecutableModule;


public class EmgStandaloneExample_Old extends EpsilonStandalone {
	
	private static String emgFilePath = "";
	private static String MMFilePath = "";
	private static String eplFilePath = "";
	private static ArrayList<String> patternList = new ArrayList<String>();
	private static ArrayList<String> modelList = new ArrayList<String>();
	
	public static void main() throws Exception {
	
		
		EpsilonStandalone EPS = new EmgStandaloneExample_Old(); 
		 
		
		//EMg Module
	/*	EmgModule emgModule = new EmgModule();
		emgModule.parse(URI.createFileURI(emgFilePath).toString()) ; 
		String st = URI.createFileURI(emgFilePath).toString(); 
		emgFilePath = emgFilePath.replaceAll("\\\\", "/");
		java.net.URI emgURI = java.net.URI.create("file:/"+emgFilePath);
		emgModule.parse(emgURI);
		*/
		String add;
		for (int i = 0; i<patternList.size();i++)
		{
			
			int j  = patternList.get(i).lastIndexOf(".");
			add= patternList.get(i).substring(0, j) + ".model";
			File outputfile = new File(add);
			outputfile.createNewFile();
			modelList.add(add);
			System.out.println("Model List: "+modelList.get(i));
		}
		List<IModel> models = EPS.getModels() ;
		//Module for each Epl File
		eplFilePath = null;
		
		long Start = System.nanoTime();
		for(int i =0;i<patternList.size();i++){
			
			EmgModule emgModule = new EmgModule();
			emgModule.parse(URI.createFileURI(emgFilePath).toString()) ; 
			String st = URI.createFileURI(emgFilePath).toString(); 
			emgFilePath = emgFilePath.replaceAll("\\\\", "/");
			java.net.URI emgURI = java.net.URI.create("file:/"+emgFilePath);
			emgModule.parse(emgURI);
			emgModule.parse(URI.createFileURI(patternList.get(i)).toString()) ; 
			eplFilePath = (patternList.get(i)).replaceAll("\\\\", "/");
			java.net.URI eplURI = java.net.URI.create("file:/"+eplFilePath) ; 
			emgModule.parse(eplURI) ;
			IModel Testmodel= models.get(i);
			emgModule.getContext().getModelRepository().addModel(Testmodel);
			emgModule.execute();
		}
		long End = System.nanoTime();
		System.out.print(End - Start);
		
}	
	@Override
	public IEolExecutableModule createModule() {
		return new EmgModule();
	}
	
	@Override
	public List<IModel> getModels() throws Exception {
		
		List<IModel> models = new ArrayList<IModel>();
		
		for (int i =0; i<modelList.size();i++)
		{
			models.add(createModel("Person", modelList.get(i), MMFilePath, false, true));
		}
		return models;
	}
	
	
	protected EmfModel createModel(String name, String model,String metamodel, boolean readOnLoad, boolean storeOnDisposal) 
					throws EolModelLoadingException, URISyntaxException {
		EmfModel emfModel = new EmfModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name);
		properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, URI.createFileURI(metamodel).toString());
		properties.put(EmfModel.PROPERTY_MODEL_URI,URI.createFileURI(model).toString());
		properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL,storeOnDisposal + "");
		emfModel.load(properties, "");
		
		return emfModel;
	}
	
	
	@Override
	public String getSource() throws Exception {
		return emgFilePath;
	}

	@Override
	public void postProcess() {}
	
	
	public List<ModuleElement> getModuleElements() {
		return Collections.emptyList();
	}
	
	public EmgModule getModule(){
		return null;
	}
	
	public void SetEmgFilePath(String EmgPath)
	{
		emgFilePath = EmgPath;
	}
	
	public void SetMMFilePath(String MMpath)
	{
		MMFilePath = MMpath;
	}
	
	public void SetPatterns(ArrayList<String> patterns)
	{
		patternList = patterns;
		System.out.println(patterns);
	}
	
	public void SetModels(ArrayList<String> models)
	{
		modelList = models;
		
	}
	public ArrayList<String> getOutputModels()
	{
		return modelList;
	}
}