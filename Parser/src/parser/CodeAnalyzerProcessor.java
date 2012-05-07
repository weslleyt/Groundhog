package parser;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("*")
public class CodeAnalyzerProcessor extends AbstractProcessor {	
    private Trees trees;
    public CollectedData data;
    
    public CodeAnalyzerProcessor(CollectedData data) {
    	this.data = data;
	}
    
    @Override
    public void init(ProcessingEnvironment pe) {
        super.init(pe);
        trees = Trees.instance(pe);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnvironment) {

        CodeAnalyzerTreeVisitor visitor = new CodeAnalyzerTreeVisitor(data);

        for (Element e : roundEnvironment.getRootElements()) {
            TreePath tp = trees.getPath(e);
           	
            if (tp != null){
            	CompilationUnitTree compUnitTree = tp.getCompilationUnit();
               	
               	List<? extends ImportTree> imports = compUnitTree.getImports();
            	String compUnitTreeString = compUnitTree.toString();
            	
            	countAllLines(compUnitTreeString);
            	
            	Iterator<? extends ImportTree> it = imports.iterator();
            	
            	while (it.hasNext()){
            		Tree tree = it.next();
            		if (tree.toString().contains("java.util.concurrent")){
            			data.importJuc++;
            		}
//            		if (tree.toString().contains(".concurrent.")){
//            			data.concurrent++;
//            		}
            	}
            	
            	visitor.scan(tp, trees);
    		}
        }

        return true;
    }
    
    private void countAllLines(String codeFile){
    	
    	// Create a pattern to match comments
        Pattern p = 
            Pattern.compile("(/\\*(?>(?:(?>[^*]+)|\\*(?!/))*)\\*/)|(//.*$)|(\"[\\s\\w]*\")"
            		, Pattern.MULTILINE);
        
        Matcher m = p.matcher(codeFile);
        String comment;
        while (m.find())  
        {
        	Pattern pC = 
                Pattern.compile("\n"
                		, Pattern.MULTILINE);
        	comment = m.group(); 
        	Matcher mC = pC.matcher(comment);
        	while(mC.find())
        		data.lineOfCode--;
        }	    	
    	
    	p = Pattern.compile("\n"
	    		, Pattern.MULTILINE);
		m = p.matcher(codeFile);
	    while (m.find())
	    	data.lineOfCode++;
    }    
    

}
