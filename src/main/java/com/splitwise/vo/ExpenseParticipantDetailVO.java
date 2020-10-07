package com.splitwise.vo;

public class ExpenseParticipantDetailVO {

	private UserBasicVO participantDetail;
	
	private int share;
	
	private double contribution_exact;
	
	private int contribution_percentage;

	public UserBasicVO getParticipantDetail() {
		return participantDetail;
	}

	public void setParticipantDetail(UserBasicVO participantDetail) {
		this.participantDetail = participantDetail;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	public double getContribution_exact() {
		return contribution_exact;
	}

	public void setContribution_exact(double contribution_exact) {
		this.contribution_exact = contribution_exact;
	}

	public int getContribution_percentage() {
		return contribution_percentage;
	}

	public void setContribution_percentage(int contribution_percentage) {
		this.contribution_percentage = contribution_percentage;
	}
	
}
