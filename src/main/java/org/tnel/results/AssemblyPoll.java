package org.tnel.results;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AssemblyPoll {
	
	String urlbase = "http://eciresults.nic.in/Constituencywise";
	String suffix = ".htm?ac=";
	int start = 1;
	int end=234;
	String statecode = "22";
	String fileName = "results.txt";
	String type = "S";
	
	public AssemblyPoll() {
		
	}
	
	public AssemblyPoll(String stateCode, int maxConst, String fileName) {
		this.statecode = stateCode;
		this.end = maxConst;
		this.fileName = fileName;
	}
	public AssemblyPoll(String stateCode, int maxConst, String fileName,String type) {
		this.statecode = stateCode;
		this.end = maxConst;
		this.fileName = fileName;
		this.type = type;
	}	
	
	public Result getResult( int ac) throws Exception {
		String requstURL = urlbase+type+statecode+ac+suffix+ac;
		System.out.println(requstURL);
		Connection connection = Jsoup.connect(requstURL);
		connection.userAgent("Mozilla").header("Cache-control", "no-cache").header("Cache-store", "no-store");
		Result result = new Result();	
		//Set proxy here if required
		//connection.proxy("proxyserver.com", 8080);
		Document doc = connection.get();
		Elements tableElements = doc.getElementById("div1").getElementsByTag("table");
		if(tableElements.size() > 0) {
			Element tableElem = tableElements.get(0);
			Elements trElements = tableElem.getElementsByTag("tr");
			if(trElements.size() > 1) {
				String acText = trElements.get(0).getElementsByTag("td").get(0).text();
				result.setAcName(acText.trim());
				/*
				if(acText.length() > 14) {
					//West Bengal - Murarai
					result.setAcName(acText.substring(13));
				} else {
					result.setAcName(acText);					
				}
				*/
				String status = trElements.get(1).getElementsByTag("td").get(0).text().trim();
				result.setStatus(status);
			}
			if(result.getStatus().equals("Result Declared")) {
				ArrayList<CandidateResult> alCandidateResults = new ArrayList<CandidateResult>();
				for(int i=3;i<trElements.size();i++) {
					Elements candidateResultElems = trElements.get(i).getElementsByTag("td");
					CandidateResult candResult = new CandidateResult();
					candResult.setName(candidateResultElems.get(0).text().trim());
					candResult.setParty(candidateResultElems.get(1).text().trim());
					candResult.setVotes(Integer.parseInt(candidateResultElems.get(2).text().trim()));
					alCandidateResults.add(candResult);
				}
				result.setAlCandidateResults(alCandidateResults);
			}
		}					
		return result;
	}

	public void getAllResults() throws Exception {
		FileOutputStream fos = new FileOutputStream(new File(fileName));
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bos.write(("Constituency No.\tConstituency Name\tCandidate Name\tParty\tVotes Polled\tPosition\tWin/Lose Margin\n").getBytes());
		for(int i=start;i<=end;i++) {
			Result result = getResult(i);
			if(result.getStatus().equals("Result Declared")) {
				Collections.sort(result.getAlCandidateResults());
				int position = 1;
				for(CandidateResult candResult : result.getAlCandidateResults()) {
					if(position <= 2) {
						bos.write((i+"\t"+result.getAcName()+"\t"+candResult.getName()+"\t"+candResult.getParty()+"\t"+candResult.getVotes()+"\t"+position+"\t"+result.getWinningMargin()+"\n").getBytes());	
					} else {
						bos.write((i+"\t"+result.getAcName()+"\t"+candResult.getName()+"\t"+candResult.getParty()+"\t"+candResult.getVotes()+"\t"+position+"\t\n").getBytes());
					}
					
					position++;
					bos.flush();
					fos.flush();
				}
			}
			System.out.println(i+" - Result captured for "+result.acName);
		}
		bos.flush();
		bos.close();
		fos.close();
	}
	
	public static void main(String args[]) throws Exception {
		AssemblyPoll poll;
		if(args.length == 3) {
			poll = new AssemblyPoll(args[0],Integer.parseInt(args[1]),args[2]);
		} else if(args.length == 4) {
			poll = new AssemblyPoll(args[0],Integer.parseInt(args[1]),args[2],args[3]);
		} else {
			poll = new AssemblyPoll();
		}
		File file = new File(poll.fileName);
		file.delete();
		poll.getAllResults();
	}
}
