class Connection{
    	private ConnectionStatus status;
    	
    	public Connection() {
    		this.status = ConnectionStatus.UNVERIFED;
    	}
    	
    	public void setVerified() {
    		this.status = ConnectionStatus.VERIFIED;
    	}
    	
    	public void setUnVerified() {
    		this.status = ConnectionStatus.UNVERIFED;
    	}
    	
    	public boolean isVerified() {
    		return this.status == ConnectionStatus.VERIFIED;
    	}
    	
    	public boolean isUnVerified() {
    		return this.status == ConnectionStatus.UNVERIFED;
    	}
    	
    	public ConnectionStatus getStatus() {
    		return this.status;
    	}
    }