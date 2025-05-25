package com.ap_graphics.model.enums;

public enum MusicPlaylist
{
    TAYLOR_SWIFT(new String[]
        {
        "audio/music/Taylor_Swift/22 - Taylor Swift.mp3",
        "audio/music/Taylor_Swift/Anti-Hero - Taylor Swift.mp3",
        "audio/music/Taylor_Swift/Cruel Summer - Taylor Swift.mp3",
        "audio/music/Taylor_Swift/Florida!!! (feat. Florence + The Machine) - Taylor Swift.mp3",
        "audio/music/Taylor_Swift/Fortnight (feat. Post Malone) - Taylor Swift.mp3",
        "audio/music/Taylor_Swift/I Can Do It With a Broken Heart - Taylor Swift.mp3",
        "audio/music/Taylor_Swift/Look What You Made Me Do - Taylor Swift.mp3",
        "audio/music/Taylor_Swift/Lover - Taylor Swift.mp3",
        "audio/music/Taylor_Swift/epiphany - Taylor Swift.mp3",
        "audio/music/Taylor_Swift/willow - Taylor Swift.mp3"
    }),

    UNDERTALE(new String[]
        {
        "audio/music/Undertale/Bonetrousle - Toby Fox.mp3",
        "audio/music/Undertale/Fallen Down - Toby Fox.mp3",
        "audio/music/Undertale/Heartache - Toby Fox.mp3",
        "audio/music/Undertale/Home (Music Box) - Toby Fox.mp3",
        "audio/music/Undertale/MEGALOVANIA - Toby Fox.mp3",
        "audio/music/Undertale/Nyeh Heh Heh! - Toby Fox.mp3",
        "audio/music/Undertale/Run! - Toby Fox.mp3",
        "audio/music/Undertale/Song That Might Play When You Fight Sans - Toby Fox.mp3",
        "audio/music/Undertale/Spear of Justice - Toby Fox.mp3",
        "audio/music/Undertale/Spookwave - Toby Fox.mp3"
    });

    private final String[] tracks;

    MusicPlaylist(String[] tracks)
    {
        this.tracks = tracks;
    }

    public String[] getTracks()
    {
        return tracks;
    }

    public String getNextTrack(int currentIndex)
    {
        if (tracks.length == 0) return null;
        return tracks[(currentIndex + 1) % tracks.length];
    }

    public int getSize()
    {
        return tracks.length;
    }
}
