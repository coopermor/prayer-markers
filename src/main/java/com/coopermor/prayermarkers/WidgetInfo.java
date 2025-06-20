package com.coopermor.prayermarkers;

public enum WidgetInfo
{
	PRAYER_THICK_SKIN(WidgetID.PRAYER_ID, WidgetID.Prayer.THICK_SKIN),
	PRAYER_BURST_OF_STRENGTH(WidgetID.PRAYER_ID, WidgetID.Prayer.BURST_OF_STRENGTH),
	PRAYER_CLARITY_OF_THOUGHT(WidgetID.PRAYER_ID, WidgetID.Prayer.CLARITY_OF_THOUGHT),
	PRAYER_SHARP_EYE(WidgetID.PRAYER_ID, WidgetID.Prayer.SHARP_EYE),
	PRAYER_MYSTIC_WILL(WidgetID.PRAYER_ID, WidgetID.Prayer.MYSTIC_WILL),
	PRAYER_ROCK_SKIN(WidgetID.PRAYER_ID, WidgetID.Prayer.ROCK_SKIN),
	PRAYER_SUPERHUMAN_STRENGTH(WidgetID.PRAYER_ID, WidgetID.Prayer.SUPERHUMAN_STRENGTH),
	PRAYER_IMPROVED_REFLEXES(WidgetID.PRAYER_ID, WidgetID.Prayer.IMPROVED_REFLEXES),
	PRAYER_RAPID_RESTORE(WidgetID.PRAYER_ID, WidgetID.Prayer.RAPID_RESTORE),
	PRAYER_RAPID_HEAL(WidgetID.PRAYER_ID, WidgetID.Prayer.RAPID_HEAL),
	PRAYER_PROTECT_ITEM(WidgetID.PRAYER_ID, WidgetID.Prayer.PROTECT_ITEM),
	PRAYER_HAWK_EYE(WidgetID.PRAYER_ID, WidgetID.Prayer.HAWK_EYE),
	PRAYER_MYSTIC_LORE(WidgetID.PRAYER_ID, WidgetID.Prayer.MYSTIC_LORE),
	PRAYER_STEEL_SKIN(WidgetID.PRAYER_ID, WidgetID.Prayer.STEEL_SKIN),
	PRAYER_ULTIMATE_STRENGTH(WidgetID.PRAYER_ID, WidgetID.Prayer.ULTIMATE_STRENGTH),
	PRAYER_INCREDIBLE_REFLEXES(WidgetID.PRAYER_ID, WidgetID.Prayer.INCREDIBLE_REFLEXES),
	PRAYER_PROTECT_FROM_MAGIC(WidgetID.PRAYER_ID, WidgetID.Prayer.PROTECT_FROM_MAGIC),
	PRAYER_PROTECT_FROM_MISSILES(WidgetID.PRAYER_ID, WidgetID.Prayer.PROTECT_FROM_MISSILES),
	PRAYER_PROTECT_FROM_MELEE(WidgetID.PRAYER_ID, WidgetID.Prayer.PROTECT_FROM_MELEE),
	PRAYER_EAGLE_EYE(WidgetID.PRAYER_ID, WidgetID.Prayer.EAGLE_EYE),
	PRAYER_MYSTIC_MIGHT(WidgetID.PRAYER_ID, WidgetID.Prayer.MYSTIC_MIGHT),
	PRAYER_RETRIBUTION(WidgetID.PRAYER_ID, WidgetID.Prayer.RETRIBUTION),
	PRAYER_REDEMPTION(WidgetID.PRAYER_ID, WidgetID.Prayer.REDEMPTION),
	PRAYER_SMITE(WidgetID.PRAYER_ID, WidgetID.Prayer.SMITE),
	PRAYER_PRESERVE(WidgetID.PRAYER_ID, WidgetID.Prayer.PRESERVE),
	PRAYER_CHIVALRY(WidgetID.PRAYER_ID, WidgetID.Prayer.CHIVALRY),
	PRAYER_PIETY(WidgetID.PRAYER_ID, WidgetID.Prayer.PIETY),
	PRAYER_RIGOUR(WidgetID.PRAYER_ID, WidgetID.Prayer.RIGOUR),
	PRAYER_AUGURY(WidgetID.PRAYER_ID, WidgetID.Prayer.AUGURY);

	private final int groupId;
	private final int childId;

	WidgetInfo(int groupId, int childId)
	{
		this.groupId = groupId;
		this.childId = childId;
	}

	public int getId()
	{
		return groupId << 16 | childId;
	}
}
