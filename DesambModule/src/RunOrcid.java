import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import bdNeo4j.ConnectNeo4j;
import desambiguation.DesambiguationTypes;
import desambiguation.ShortenerName;
import desambiguation.StringDesambiguation;
import institution.UnBOrcid;
import orcid.ClientPubApiOrcid;
import publication.*;
import webcrawler.DBLPCrawler;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.*;
import org.orcid.jaxb.model.record_v2.OtherName;
import org.orcid.jaxb.model.record_v2.OtherNames;
import org.orcid.jaxb.model.record_v2.Record;


public class RunOrcid {

	public static void main(String[] args) throws IOException, JAXBException {
		
		//Executando a busca no Orcid dos autores da UnB e colocando no BD NEO4J
		UnBOrcid unb = new UnBOrcid();
		unb.CarregaAutores();
		//Carregando os BibTex
		//unb.CarregaBibTexAutores();
		
		
		
	}


}
