package org.tnel.results;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class ResultTest {

	@Test
	public void test() {
		Result result = new Result();
		CandidateResult cr1 = new CandidateResult();
		cr1.setName("cand one");
		cr1.setParty("party one");
		cr1.setVotes(124123);
		CandidateResult cr2 = new CandidateResult();
		cr2.setName("cand two");
		cr2.setParty("party two");
		cr2.setVotes(104123);
		CandidateResult cr3 = new CandidateResult();
		cr3.setName("cand three");
		cr3.setParty("party three");
		cr3.setVotes(84123);
		ArrayList<CandidateResult> alCr = new ArrayList<CandidateResult>();
		alCr.add(cr1);
		alCr.add(cr2);
		alCr.add(cr3);
		result.setAlCandidateResults(alCr);
		assertNotNull(result.getWinner());
		assertEquals("cand one", result.getWinner().getName());
		assertNotNull(result.getRunnerUp());
		assertEquals("cand two", result.getRunnerUp().getName());
		assertEquals((124123-104123), result.getWinningMargin());
		assertEquals((124123+104123+84123), result.getTotalPolledVotes());
		System.out.println(result.getWinningMarginPercentage());
		assertEquals((124123-104123)*100.0/(124123+104123+84123), result.getWinningMarginPercentage(),0.01);
		
	}
	
	@Test
	public void sort() {
		CandidateResult cr1 = new CandidateResult();
		cr1.setName("cand one");
		cr1.setParty("party one");
		cr1.setVotes(124123);
		CandidateResult cr2 = new CandidateResult();
		cr2.setName("cand two");
		cr2.setParty("party two");
		cr2.setVotes(104123);
		CandidateResult cr3 = new CandidateResult();
		cr3.setName("cand three");
		cr3.setParty("party three");
		cr3.setVotes(84123);
		ArrayList<CandidateResult> alCr = new ArrayList<CandidateResult>();
		alCr.add(cr3);
		alCr.add(cr2);
		alCr.add(cr1);
		Collections.sort(alCr);
		assertEquals("cand two", alCr.get(1).getName());
		assertEquals("cand three", alCr.get(2).getName());
	}

}
