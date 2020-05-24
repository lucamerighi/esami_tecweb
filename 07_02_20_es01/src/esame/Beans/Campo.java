package esame.Beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Campo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Player player1;
	public Player player2;
	LocalDateTime time ;
	LocalDateTime start;
	
	public Campo() {
		super();
		player1=new Player("default","default",'d');
		player2=new Player("default","default",'d');
		time =LocalDateTime.now();
		start=LocalDateTime.now();
	}
	
	public void setPlayersDefault() {
		player1=new Player("default","default",'d');
		player2=new Player("default","default",'d');
	}
	
	public void setPlayer2(Player player2) {
		this.player2 =player2;
		start=LocalDateTime.now();
	}
	
	public void notifyPlayer() {
		player1.notify=true;
		player2.notify=true;
	}
	
	
	public void outRichiesta(List<Player> players) {
			if (!player1.nome.equals("default") || player2.nome.equals("default")) {
				if (LocalDateTime.now().minusHours(72).isAfter(time)) {
					for (Player x : players) {
						if (x.nome.equals(player1.nome)){
							x.notify=true;
						}
					}
					this.setPlayersDefault();
					start=LocalDateTime.now();
					time=LocalDateTime.now();
					this.notifyPlayer();
				}
			}
	}
	
	public void timeOver(List<Player> players) {
		if (!player1.nome.equals("default") && !player2.nome.equals("default")) {
			if (LocalDateTime.now().minusHours(1).isAfter(start)) {
				for (Player x : players) {
					if (x.nome.equals(player1.nome) || x.nome.equals(player2.nome)){
						x.notify=true;
					}
				}
				this.setPlayersDefault();
				start=LocalDateTime.now();
				time=LocalDateTime.now();
				this.notifyPlayer();
			}
		}
	}
	
	public boolean isForYou(Player p) {
		boolean result = false;
		if (!player1.nome.equals("default") && player2.nome.equals("default") && p.chr==player1.chr) {
			player2=p;
			result=true;
		}
		return result;
	}
	
	public boolean available(Player p) {
		boolean result = false;
		if (player1.nome.equals("default") && player2.nome.equals("default")) {
			player1=p;
			time=LocalDateTime.now();
			result=true;
		}
		return result;
	}
	
	
	
}
