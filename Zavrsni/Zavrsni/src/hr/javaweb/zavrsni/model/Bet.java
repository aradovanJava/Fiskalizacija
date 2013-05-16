package hr.javaweb.zavrsni.model;





public class Bet {
	
	private String first_player;
	private String second_player;
	private float first_player_odd;
	private float second_player_odd;
	private String playing_time;
	private String playing_date;
	
	
	
	public String getFirst_player() {
		return first_player;
	}
	public void setFirst_player(String first_player) {
		this.first_player = first_player;
	}
	public String getSecond_player() {
		return second_player;
	}
	public void setSecond_player(String second_player) {
		this.second_player = second_player;
	}
	public float getFirst_player_odd() {
		return first_player_odd;
	}
	public void setFirst_player_odd(float first_player_odd) {
		this.first_player_odd = first_player_odd;
	}
	public float getSecond_player_odd() {
		return second_player_odd;
	}
	public void setSecond_player_odd(float second_player_odd) {
		this.second_player_odd = second_player_odd;
	}
	public String getPlaying_time() {
		return playing_time;
	}
	public void setPlaying_time(String playing_time) {
		this.playing_time = playing_time;
	}
	public String getPlaying_date() {
		return playing_date;
	}
	public void setPlaying_date(String playing_date) {
		this.playing_date = playing_date;
	}
	
	public Bet(){
	}
	
	
	public Bet(String first_player, String second_player, float first_player_odd, float second_player_odd, String playing_time, String playing_date) {
		this.first_player = first_player;
		this.second_player = second_player;
		this.first_player_odd = first_player_odd;
		this.second_player_odd = second_player_odd;
		this.playing_time = playing_time;
		this.playing_date = playing_date;
	}
	
	@Override
	public int hashCode() {
		
		int result = 1;
		
		result = 9 * result + ((first_player != null) ? first_player.hashCode(): 0);
		result = 9 * result + (int)(first_player_odd);
		result = 9 * result + (int)(second_player_odd);
		result = 9 * result + ((playing_date != null) ? playing_date.hashCode() : 0);
		result = 9 * result + ((playing_time != null) ? playing_time.hashCode() : 0);
		result = 9 * result + ((second_player != null) ? second_player.hashCode(): 0);
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)return true;
		if (obj == null || getClass() != obj.getClass())return false;
		
		Bet other = (Bet) obj;
		if (first_player_odd != other.first_player_odd || second_player_odd != other.second_player_odd) return false;
		if (first_player != null ? first_player.equals(other.first_player) : other.first_player != null) return false;
		if (second_player != null ? second_player.equals(other.second_player) : other.second_player != null) return false;
		if (playing_time != null ? playing_time.equals(other.playing_time) : other.playing_time != null) return false;
		if(playing_date != null ? playing_date.equals(other.playing_date) : other.playing_date != null) return false;
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
