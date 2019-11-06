package io.rainrobot.wake.rest.dto;

import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

;

@Embeddable
public class SoundOptions {
	
	@Enumerated
	private Sound sound;

	private int volume;

	private int playForInSeconds;

	public Sound getSound() {
		return sound;
	}

	public void setSound(Sound sound) {
		this.sound = sound;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getPlayForInSeconds() {
		return playForInSeconds;
	}

	public void setPlayForInSeconds(int playForInSeconds) {
		this.playForInSeconds = playForInSeconds;
	}


	
	
}
