package com.ap_graphics.model.enums;

import com.ap_graphics.model.App;
import com.ap_graphics.model.Player;

public enum MenuTexts
{
    MAIN_MENU("Main Menu", "メインメニュー"),
    SCORE("Score: ", "スコア："),
    SETTINGS("Settings", "セッティング"),
    SCORE_BOARD("Scoreboard", "スコアボード"),
    NEW_GAME("New Game", "ニューゲーム"),
    CONTINUE("Continue", "コンティニュー"),
    HINTS("Hints", "ヒント"),
    LOGOUT("Logout", "ログアウト"),
    PROFILE("Profile", "プロフィール"),

    HINT_MENU("Hint Menu", "ヒントメニュー"),
    HERO_GUIDE("Hero Guide", "ヒーローガイド"),
    GAME_KEYS("Game Keys", "ゲームキー"),
    CHEAT_CODES("Cheat Codes", "チートコード"),
    ABILITIES("Abilities", "アビリティ"),

    GO_BACK("Go Back", "戻る"),

    ADVANCE_TIME("T: advance time 1 minute", "T：1分進める"),
    LEVEL_UP("L: levels up hero", "L：レベルアップ"),
    INCREASE_HP("H: increases your hp", "H：体力回復"),
    BOSS_FIGHT("B: takes you to boss fight", "B：ボス戦へ移動"),
    KILL_ALL("K: kills all current enemies", "K：敵全滅"),
    PAUSE_MENU("P: pause menu", "P：ポーズメニュー"),
    INSTANT_EXIT("F: instantly closes the game", "F：即終了"),
    AUTO_AIM("C: triggers auto aim", "C：自動エイム"),
    AUTO_RELOAD("M: triggers auto reload", "M：自動リロード"),

    INCREASE_HP_AB("Increase HP", "HP増加"),
    HP_EFFECT("+1 Max HP", "最大HP＋1"),
    INCREASE_DMG("Increase Damage", "ダメージ増加"),
    DMG_EFFECT("+25% Weapon Damage for 10s", "10秒間攻撃力＋25%"),
    INCREASE_PROJECTILES("Increase Projectiles", "弾数増加"),
    PROJECTILES_EFFECT("+1 Projectile", "弾＋1"),
    INCREASE_AMMO("Increase Ammo Capacity", "弾薬容量増加"),
    AMMO_EFFECT("+5 Max Ammo", "最大弾薬＋5"),
    INCREASE_SPEED("Increase Speed", "スピード増加"),
    SPEED_EFFECT("2x Move Speed for 10s", "10秒間移動2倍"),

    // Directions
    UP("Up", "上"),
    DOWN("Down", "下"),
    LEFT("Left", "左"),
    RIGHT("Right", "右"),

    // Stat labels
    HP("hp", "体力"),
    SPEED("speed", "スピード"),

    // Hero names (optional, but for full Japanese mode)
    SHANA("Shana", "シャナ"),
    DIAMOND("Diamond", "ダイアモンド"),
    SCARLET("Scarlet", "スカーレット"),
    LILITH("Lilith", "リリス"),
    DASHER("Dasher", "ダッシャー"),

    USERNAME("username", "ユーザー名"),
    SCORE_HEADER("score", "スコア"),
    GAME_TIME("game time", "プレイ時間"),
    KILL_COUNT("kill count", "キル数"),

    PROFILE_MENU("Profile Menu", "プロフィールメニュー"),
    CHANGE_USERNAME("Change Username", "ユーザー名変更"),
    CHANGE_PASSWORD("Change Password", "パスワード変更"),
    CHANGE_AVATAR("Change Avatar", "アバター変更"),
    DELETE_ACCOUNT("Delete Account", "アカウント削除"),

    CHANGE_USERNAME_TITLE("Change Username", "ユーザー名を変更"),
    CHANGE_USERNAME_PROMPT("New Username:", "新しいユーザー名："),

    CHANGE_PASSWORD_TITLE("Change Password", "パスワードを変更"),
    CHANGE_PASSWORD_PROMPT("New Password:", "新しいパスワード："),

    SHOW_PASSWORD("Show Password", "パスワードを表示"),
    HIDE_PASSWORD("Hide Password", "パスワードを非表示"),

    CHANGE("Change", "変更"),
    CANCEL("Cancel", "キャンセル"),

    USERNAME_TAKEN("Username already taken!", "このユーザー名は既に使われています！"),
    USERNAME_AVAILABLE("Username available", "このユーザー名は利用可能です"),
    PASSWORD_TOO_SHORT("password too short!", "パスワードが短すぎます！"),
    PASSWORD_NO_CAPITAL("password must have a capital letter!", "パスワードに大文字が必要です！"),
    PASSWORD_NO_DIGIT("password must have a digit!", "パスワードに数字が必要です！"),
    PASSWORD_NO_SPECIAL("password must have a special character!", "パスワードに記号が必要です！"),
    PASSWORD_STRONG("password is strong", "強力なパスワードです"),

    UPDATE_SUCCESSFUL("%s updated successfully!", "%sの更新に成功しました！"),
    UPDATE_FAILED("Update failed!", "更新に失敗しました！"),

    CONFIRM("Confirm", "確認"),
    YES("Yes", "はい"),
    NO("No", "いいえ"),
    CONFIRM_DELETION("Confirm Deletion", "削除の確認"),
    DELETE_ACCOUNT_MSG("Are you sure you want to delete your account?", "アカウントを削除してもよろしいですか？"),

    PRE_GAME_MENU("Pre Game Menu", "プリゲームメニュー"),
    START_NEW_GAME("Start New Game", "新しいゲームを始める"),
    MINUTES("minutes", "分"),

    TIMER("Time", "時間"),
    AMMO("Ammo", "弾薬"),
    KILL_COUNT_GAME("Kill Count", "キル数"),
    XP("XP", "経験値"),
    LEVEL("Level", "レベル"),
    HP_GAME("HP", "体力"),
    AUTO_AIM_GAME("Auto Aim", "自動照準"),
    AUTO_RELOAD_GAME("Auto Reload", "自動リロード"),
    ON("On", "オン"),
    OFF("Off", "オフ"),

    SETTINGS_MENU("Settings Menu", "設定メニュー"),
    MUSIC_VOLUME("Music Volume", "音楽の音量"),
    PLAYLIST("Playlist:", "プレイリスト:"),
    SONG("Song:", "曲:"),
    ENABLE_SFX("Enable SFX", "効果音を有効にする"),
    ENABLE_AUTO_RELOAD("Enable Auto-Reload", "自動リロードを有効にする"),
    LANGUAGE_ENGLISH("Language: English", "言語：英語"),
    BW_MODE("Black & White Mode", "白黒モード"),
    CHANGE_MOVEMENT_KEYS("Change Movement Keys", "移動キーを変更"),
    REMAP_MOVEMENT_KEYS("Remap Movement Keys", "移動キーの割り当て"),
    REMAPPING_INSTRUCTION("Click a button, then press a new key", "ボタンを押して、新しいキーを入力"),
    INVALID_KEY("INVALID", "無効なキー"),
    DIRECTION_UP("Up", "上"),
    DIRECTION_DOWN("Down", "下"),
    DIRECTION_LEFT("Left", "左"),
    DIRECTION_RIGHT("Right", "右"),
    DONE("Done", "完了"),
    ;

    private final String EN;
    private final String JA;

    MenuTexts(String EN, String JA)
    {
        this.EN = EN;
        this.JA = JA;
    }

    public String getText()
    {
        Player player = App.getCurrentPlayer();
        if (player == null)
        {
            return EN;
        }

        if (player.lovesJapan())
        {
            return JA;
        }

        return EN;
    }
}
