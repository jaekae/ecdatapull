package org.tnel.results;

public class CandidateResult implements Comparable<CandidateResult> {
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParty() {
		return party;
	}
	public void setParty(String party) {
		this.party = party;
	}
	public int getVotes() {
		return votes;
	}
	public void setVotes(int votes) {
		this.votes = votes;
	}
	String name;
	String party;
	int votes;
	
	@Override
	public int compareTo(CandidateResult arg0) {
		if (this.votes < arg0.votes) {
			return 1;
		} else if (this.votes > arg0.votes) {
			return -1;
		}   else {
			return 0;	
		}		
	}
}
