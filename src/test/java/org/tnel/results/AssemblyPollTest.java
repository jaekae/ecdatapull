package org.tnel.results;

import static org.junit.Assert.assertNotNull;

import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.client.HttpClient;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;

public class AssemblyPollTest {

	public void getResult() throws Exception{
		AssemblyPoll poll = new AssemblyPoll();
		Result result = poll.getResult(100);
		assertNotNull(result);
		assertNotNull(result.getAcName());
		System.out.println(result.getAcName());
		assertNotNull(result.getStatus());
		System.out.println(result.getStatus());
		assertNotNull(result.getAlCandidateResults());
		for(CandidateResult candResult : result.getAlCandidateResults()) {
			System.out.println(candResult.getName()+"-"+candResult.getParty()+"-"+candResult.getVotes());
		}
	}
	
	@Test
	public void testanother() throws Exception {
		Connection connection = Jsoup.connect("http://eciresults.nic.in/ConstituencywiseS22170.htm?ac=170");
		System.out.println(connection.get().getElementById("div1"));
	}
	


}
