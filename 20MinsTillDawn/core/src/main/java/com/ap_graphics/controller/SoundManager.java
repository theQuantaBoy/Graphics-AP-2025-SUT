package com.ap_graphics.controller;

import com.ap_graphics.model.App;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.enums.MusicPlaylist;
import com.ap_graphics.model.enums.SoundEffectType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.*;

public class SoundManager
{
    private static SoundManager instance;

    private final Map<SoundEffectType, Sound> soundEffects = new HashMap<>();
    private final Queue<String> currentPlaylistQueue = new LinkedList<>();

    private Music currentMusic;
    private MusicPlaylist currentPlaylist;
    private int currentTrackIndex = 0;

    private long loopingSoundId = -1;
    private Sound loopingSound = null;

    private SoundManager()
    {
        loadSoundEffects();
    }

    public static SoundManager getInstance()
    {
        if (instance == null)
        {
            instance = new SoundManager();
        }
        return instance;
    }

    private void loadSoundEffects()
    {
        for (SoundEffectType type : SoundEffectType.values())
        {
            soundEffects.put(type, Gdx.audio.newSound(Gdx.files.internal(type.getFilePath())));
        }
    }

    public void playSFX(SoundEffectType type)
    {
        Player player = App.getCurrentPlayer();
        if (player == null || !player.isSfxEnabled()) return;

        Sound sound = soundEffects.get(type);
        if (sound != null)
        {
            sound.play(player.getSfxVolume());
        }
    }

    public void playMusic(Player player)
    {
        stopMusic();

        if (player == null)
        {
            this.currentPlaylist = MusicPlaylist.UNDERTALE;
        } else
        {
            this.currentPlaylist = player.getMusicPlaylist();
        }

        this.currentTrackIndex = 0;
        playCurrentTrack();
    }

    private void playCurrentTrack()
    {
        stopMusic(); // 🛑 Prevent overlapping music

        if (currentPlaylist == null) {
            currentPlaylist = MusicPlaylist.UNDERTALE;
            currentTrackIndex = 0;
        }

        String[] tracks = currentPlaylist.getTracks();
        if (tracks.length == 0) return;

        String trackPath = tracks[currentTrackIndex];

        try {
            currentMusic = Gdx.audio.newMusic(Gdx.files.internal(trackPath));
        } catch (Exception e) {
            Gdx.app.error("SoundManager", "Failed to load track: " + trackPath, e);
            return;
        }

        Player player = App.getCurrentPlayer();
        float volume = (player == null) ? 1.0f : player.getMusicVolume();

        currentMusic.setLooping(false);
        currentMusic.setVolume(volume);

        currentMusic.setOnCompletionListener(music -> {
            currentTrackIndex = (currentTrackIndex + 1) % tracks.length;
            playCurrentTrack(); // 🔄 Loop to next song
        });

        Gdx.app.log("SoundManager", "Playing music: " + trackPath);
        currentMusic.play();
    }


    public void stopMusic()
    {
        if (currentMusic != null)
        {
            currentMusic.stop();
            currentMusic.dispose();
            currentMusic = null;
        }
    }

    public void pauseMusic()
    {
        if (currentMusic != null) currentMusic.pause();
    }

    public void resumeMusic()
    {
        if (currentMusic != null && App.getCurrentPlayer().isMusicEnabled())
        {
            currentMusic.play();
        }
    }

    public void updatePlayerSettings()
    {
        Player player = App.getCurrentPlayer();
        if (player == null) return;

        if (currentMusic != null)
        {
            currentMusic.setVolume(player.getMusicVolume());
            if (!player.isMusicEnabled())
            {
                currentMusic.pause();
            } else if (!currentMusic.isPlaying())
            {
                currentMusic.play();
            }
        }
    }

    public MusicPlaylist getCurrentPlaylist()
    {
        return currentPlaylist;
    }

    public void dispose()
    {
        for (Sound sound : soundEffects.values())
        {
            sound.dispose();
        }
        stopMusic();
    }

    public String getCurrentTrackName()
    {
        if (currentPlaylist == null) return "No Track";
        return currentPlaylist.getTrackName(currentTrackIndex);
    }

    public void playTrack(MusicPlaylist playlist, int index)
    {
        stopMusic();
        this.currentPlaylist = playlist;
        this.currentTrackIndex = index % playlist.getSize();
        playCurrentTrack();
    }

    public void playNextTrack()
    {
        if (currentPlaylist == null) return;
        currentTrackIndex = (currentTrackIndex + 1) % currentPlaylist.getSize();
        playCurrentTrack();
    }

    public void playPreviousTrack()
    {
        if (currentPlaylist == null) return;
        currentTrackIndex = (currentTrackIndex - 1 + currentPlaylist.getSize()) % currentPlaylist.getSize();
        playCurrentTrack();
    }

    public void playLoopingSFX()
    {
        stopMusic();
        stopLoopingSFX(); // stop if already playing

        loopingSound = soundEffects.get(SoundEffectType.PRETTY_DUNGEON_LOOP);
        if (loopingSound != null)
        {
            loopingSoundId = loopingSound.loop();
        }
    }

    public void stopLoopingSFX()
    {
        if (loopingSound != null && loopingSoundId != -1)
        {
            loopingSound.stop(loopingSoundId);
            loopingSoundId = -1;
        }
    }
}
