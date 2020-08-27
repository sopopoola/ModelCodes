package Pkg.JavaCode;
 

import java.io.File;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.etl.execute.context.IEtlContext;
import org.eclipse.epsilon.eol.execute.context.EolContext;
 




public abstract class EpsilonStandalone {
	
	protected IEolExecutableModule module;
	
	protected Object result;
	
	//////////////////////////////////////////////////////
	
	protected static Parameter sourceParameter;
	protected List<Parameter> targetParameters = new ArrayList<Parameter>();
	protected static IEtlContext context;
	
	
	//////////////////////////////////////////////////////////////////////////////
	
	
	public abstract IEolExecutableModule createModule();
	
	public abstract String getSource() throws Exception;
	
	public abstract List<IModel> getModels() throws Exception;
	
	public void postProcess() {};
	
	public void preProcess() {};
	
	public void execute() throws Exception {
		
		module = createModule();
		module.parse(getFile(getSource()));
		((AST) module).getChildren();
		
		if (module.getParseProblems().size() > 0) {
			System.err.println("Parse errors occured...");
			for (ParseProblem problem : module.getParseProblems()) {
				System.err.println(problem.toString());
			}
			System.exit(-1);
		}
		
		for (IModel model : getModels()) {
			module.getContext().getModelRepository().addModel(model);
		}
		
		preProcess();
		result = execute(module);
		postProcess();
		
		module.getContext().getModelRepository().dispose();
	}
	
	protected Object execute(IEolExecutableModule module) 
			throws EolRuntimeException {
		return module.execute();
	}
	
	protected EmfModel createEmfModel(String name, String model, 
			String metamodel, boolean readOnLoad, boolean storeOnDisposal) 
					throws EolModelLoadingException, URISyntaxException {
		EmfModel emfModel = new EmfModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name);
		properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, getFile(metamodel).toURI().toString());
		properties.put(EmfModel.PROPERTY_MODEL_URI, getFile(model).toURI().toString());
		properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL,storeOnDisposal + "");
		emfModel.load(properties, "");
		
		return emfModel;
	}

	protected EmfModel createEmfModelByURI(String name, String model, 
			String metamodel, boolean readOnLoad, boolean storeOnDisposal) 
					throws EolModelLoadingException, URISyntaxException {
		EmfModel emfModel = new EmfModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name);
		properties.put(EmfModel.PROPERTY_METAMODEL_URI, metamodel);
		properties.put(EmfModel.PROPERTY_MODEL_URI, 
				getFile(model).toURI().toString());
		properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, 
				storeOnDisposal + "");
		emfModel.load(properties, "");
		return emfModel;
	}
	
	protected File getFile(String fileName) throws URISyntaxException {
		
		URI binUri = EpsilonStandalone.class.getResource(fileName).toURI();
		URI uri = null;
		
		if (binUri.toString().indexOf("bin") > -1) {
			uri = new URI(binUri.toString().replaceAll("bin", "src"));
		}
		else {
			uri = binUri;
		}
		
		return new File(uri);
	}
	

        
		 
		
		
  }
		
		
	
	
	
	
	

