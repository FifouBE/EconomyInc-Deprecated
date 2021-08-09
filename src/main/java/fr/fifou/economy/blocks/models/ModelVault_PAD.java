package fr.fifou.economy.blocks.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelVault_PAD extends ModelBase
{
    ModelRenderer Vault_Pad;

    public ModelVault_PAD()
    {
        this(0.0F);
    }

    public ModelVault_PAD(float par1)
    {
        this.Vault_Pad = new ModelRenderer(this, 181, 22);
        this.Vault_Pad.setTextureSize(256, 128);
        this.Vault_Pad.addBox(-2.0F, -4.0F, -3.0F, 4, 16, 16);
        this.Vault_Pad.setRotationPoint(-14.4F, 17.5F, 11.0F);
    }

    public void renderAll()
    {
        float f = 0.0625F;
        this.Vault_Pad.rotateAngleX = 0.0F;
        this.Vault_Pad.rotateAngleY = 0.0F;
        this.Vault_Pad.rotateAngleZ = 0.2084309F;
        this.Vault_Pad.renderWithRotation(f);
    }
}
