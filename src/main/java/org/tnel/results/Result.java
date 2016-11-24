package org.tnel.results;

import java.util.ArrayList;

public class Result {
	public String getAcName() {
		return acName;
	}
	public void setAcName(String acName) {
		this.acName = acName;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public ArrayList<CandidateResult> getAlCandidateResults() {
		return alCandidateResults;
	}
	public void setAlCandidateResults(ArrayList<CandidateResult> alCandidateResults) {
		this.alCandidateResults = alCandidateResults;
	}
	String acName;
	String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	String district;
	ArrayList<CandidateResult> alCandidateResults = new ArrayList<CandidateResult>();

	public CandidateResult getWinner() {
		if(alCandidateResults == null || alCandidateResults.size() == 0) {
			return null;	
		} else {
			return alCandidateResults.get(0);
		}
		
	}
	public CandidateResult  getRunnerUp() {
		if(alCandidateResults == null || alCandidateResults.size() < 2) {
			return null;	
		} else {
			return alCandidateResults.get(1);
		}
	}
	public int getWinningMargin() {
		if(alCandidateResults == null || alCandidateResults.size() < 1) {
			return 0;	
		} else if( alCandidateResults.size() < 2) {
			return alCandidateResults.get(0).getVotes();
		} else {
			return alCandidateResults.get(0).getVotes()-alCandidateResults.get(1).getVotes();
		}
	
	}
	
	public int getTotalPolledVotes() {
		if(alCandidateResults == null || alCandidateResults.size() < 1) {
			return 0;	
		} else {
			int total = 0;
			for(CandidateResult candRes : alCandidateResults) {
				total = total+candRes.getVotes();
			}
			return total;
		}
	}
	
	public double getWinningMarginPercentage() {
		if(getTotalPolledVotes()  == 0) {
			return 0;
		} else {
			return getWinningMargin()*100.0/getTotalPolledVotes();
		}
	
	}
}
