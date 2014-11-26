package br.com.transmetais.bean;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("E")
public class ItemPesagemEntrada extends ItemPesagem {
	@ManyToOne
	@JoinColumn(name="material_id")
	private Material material;

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
}
