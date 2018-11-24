package tile;

import java.io.Serializable;

public class Tile implements Serializable {
	private static final long serialVersionUID = 382610168546865107L;
	// 19 bytes
	public int ownerid;
	public Improvement i;


	public byte elevation; // 'Z' (Elevation)
	public byte iron_ore;
	public byte bauxite_ore;
	public byte tin_ore;
	public byte copper_ore;
	public byte gold_ore;
	public byte silver_ore;
	public byte coal_ore;
	public byte platinum_ore;
	public byte nat_gas;
	public byte oil;
	public byte gems;
	public byte wildlife;
	public byte lumber;
	public byte humidity;

	public byte getElevation() {
		return elevation;
	}

	public void setElevation(final byte elevation) {
		this.elevation = elevation;
	}

	public byte getIronOre() {
		return iron_ore;
	}

	public void setIronOre(final byte iron_ore) {
		this.iron_ore = iron_ore;
	}

	public byte getBauxiteOre() {
		return bauxite_ore;
	}

	public void setBauxiteOre(final byte bauxite_ore) {
		this.bauxite_ore = bauxite_ore;
	}

	public byte getTinOre() {
		return tin_ore;
	}

	public void setTinOre(final byte tin_ore) {
		this.tin_ore = tin_ore;
	}

	public byte getCopperOre() {
		return copper_ore;
	}

	public void setCopperOre(final byte copper_ore) {
		this.copper_ore = copper_ore;
	}

	public byte getGoldOre() {
		return gold_ore;
	}

	public void setGoldOre(final byte gold_ore) {
		this.gold_ore = gold_ore;
	}

	public byte getSilverOre() {
		return silver_ore;
	}

	public void setSilverOre(final byte silver_ore) {
		this.silver_ore = silver_ore;
	}

	public byte getCoalOre() {
		return coal_ore;
	}

	public void setCoalOre(final byte coal_ore) {
		this.coal_ore = coal_ore;
	}

	public byte getPlatinumOre() {
		return platinum_ore;
	}

	public void setPlatinumOre(final byte platinum_ore) {
		this.platinum_ore = platinum_ore;
	}

	public byte getNaturalGas() {
		return nat_gas;
	}

	public void setNaturalGas(final byte nat_gas) {
		this.nat_gas = nat_gas;
	}

	public byte getOil() {
		return oil;
	}

	public void setOil(final byte oil) {
		this.oil = oil;
	}

	public byte getGems() {
		return gems;
	}

	public void setGems(final byte gems) {
		this.gems = gems;
	}

	public byte getWildlife() {
		return wildlife;
	}

	public void setWildlife(final byte wildlife) {
		this.wildlife = wildlife;
	}

	public byte getLumber() {
		return lumber;
	}

	public void setLumber(final byte lumber) {
		this.lumber = lumber;
	}

	public byte getHumidity() {
		return humidity;
	}

	public void setHumidity(final byte humidity) {
		this.humidity = humidity;
	}
}
