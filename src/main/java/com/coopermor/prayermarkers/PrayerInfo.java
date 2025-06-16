package com.coopermor.prayermarkers;

import net.runelite.api.gameval.VarbitID;

public enum PrayerInfo {
    THICK_SKIN(VarbitID.PRAYER_THICKSKIN, "Thick Skin", WidgetInfo.PRAYER_THICK_SKIN),
    BURST_OF_STRENGTH(VarbitID.PRAYER_BURSTOFSTRENGTH, "Burst of Strength", WidgetInfo.PRAYER_BURST_OF_STRENGTH),
    CLARITY_OF_THOUGHT(VarbitID.PRAYER_CLARITYOFTHOUGHT, "Clarity of Thought", WidgetInfo.PRAYER_CLARITY_OF_THOUGHT),
    SHARP_EYE(VarbitID.PRAYER_SHARPEYE,"Sharp Eye", WidgetInfo.PRAYER_SHARP_EYE),
    MYSTIC_WILL(VarbitID.PRAYER_MYSTICWILL, "Mystic Will",WidgetInfo.PRAYER_MYSTIC_WILL),
    ROCK_SKIN(VarbitID.PRAYER_ROCKSKIN, "Rock Skin", WidgetInfo.PRAYER_ROCK_SKIN),
    SUPERHUMAN_STRENGTH(VarbitID.PRAYER_SUPERHUMANSTRENGTH, "Superhuman Strength", WidgetInfo.PRAYER_SUPERHUMAN_STRENGTH),
    IMPROVED_REFLEXES(VarbitID.PRAYER_IMPROVEDREFLEXES, "Improved Reflexes", WidgetInfo.PRAYER_IMPROVED_REFLEXES),
    RAPID_RESTORE(VarbitID.PRAYER_RAPIDRESTORE,"Rapid Restore", WidgetInfo.PRAYER_RAPID_RESTORE),
    RAPID_HEAL(VarbitID.PRAYER_RAPIDHEAL, "Rapid Heal", WidgetInfo.PRAYER_RAPID_HEAL),
    PROTECT_ITEM(VarbitID.PRAYER_PROTECTITEM, "Protect Item", WidgetInfo.PRAYER_PROTECT_ITEM),
    HAWK_EYE(VarbitID.PRAYER_HAWKEYE, "Hawk Eye", WidgetInfo.PRAYER_HAWK_EYE),
    MYSTIC_LORE(VarbitID.PRAYER_MYSTICLORE, "Mystic Lore", WidgetInfo.PRAYER_MYSTIC_LORE),
    STEEL_SKIN(VarbitID.PRAYER_STEELSKIN, "Steel Skin", WidgetInfo.PRAYER_STEEL_SKIN),
    ULTIMATE_STRENGTH(VarbitID.PRAYER_ULTIMATESTRENGTH, "Ultimate Strength",WidgetInfo.PRAYER_ULTIMATE_STRENGTH),
    INCREDIBLE_REFLEXES(VarbitID.PRAYER_INCREDIBLEREFLEXES, "Incredible Reflexes",WidgetInfo.PRAYER_INCREDIBLE_REFLEXES),
    PROTECT_FROM_MAGIC(VarbitID.PRAYER_PROTECTFROMMAGIC, "Protect from Magic", WidgetInfo.PRAYER_PROTECT_FROM_MAGIC),
    PROTECT_FROM_MISSILES(VarbitID.PRAYER_PROTECTFROMMISSILES, "Protect from Missiles", WidgetInfo.PRAYER_PROTECT_FROM_MISSILES),
    PROTECT_FROM_MELEE(VarbitID.PRAYER_PROTECTFROMMELEE, "Protect from Melee", WidgetInfo.PRAYER_PROTECT_FROM_MELEE),
    EAGLE_EYE(VarbitID.PRAYER_EAGLEEYE, "Eagle Eye/Deadeye",WidgetInfo.PRAYER_EAGLE_EYE),
    MYSTIC_MIGHT(VarbitID.PRAYER_MYSTICMIGHT, "Mystic Might/Mystic Vigour", WidgetInfo.PRAYER_MYSTIC_MIGHT),
    RETRIBUTION(VarbitID.PRAYER_RETRIBUTION, "Retribution", WidgetInfo.PRAYER_RETRIBUTION),
    REDEMPTION(VarbitID.PRAYER_REDEMPTION, "Redemption", WidgetInfo.PRAYER_REDEMPTION),
    SMITE(VarbitID.PRAYER_SMITE, "Smite", WidgetInfo.PRAYER_SMITE),
    CHIVALRY(VarbitID.PRAYER_CHIVALRY, "Chivalry", WidgetInfo.PRAYER_CHIVALRY),
    PIETY(VarbitID.PRAYER_PIETY, "Piety", WidgetInfo.PRAYER_PIETY),
    PRESERVE(VarbitID.PRAYER_PRESERVE, "Preserve", WidgetInfo.PRAYER_PRESERVE),
    RIGOUR(VarbitID.PRAYER_RIGOUR, "Rigour", WidgetInfo.PRAYER_RIGOUR),
    AUGURY(VarbitID.PRAYER_AUGURY, "Augury", WidgetInfo.PRAYER_AUGURY);

    private final int varbit;
    private final String displayName;
    private final WidgetInfo widgetInfo;

    PrayerInfo(int varbit, String displayName, WidgetInfo widgetInfo)
    {
        this.varbit = varbit;
        this.displayName = displayName;
        this.widgetInfo = widgetInfo;
    }

    public int getVarbit()
    {
        return varbit;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public WidgetInfo getWidgetInfo()
    {
        return widgetInfo;
    }

    public static int getPrayerWidgetId(PrayerInfo prayer)
    {
        return PrayerInfo.valueOf(prayer.name()).getWidgetInfo().getId();
    }
}
