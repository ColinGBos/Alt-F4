package vapourdrive.furnaceevolved.blocks;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.Loader;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import vapourdrive.furnaceevolved.Reference;

public class GuiFurnaceEvolved extends GuiContainer
{
	ResourceLocation furnaceGuiTextures = new ResourceLocation(Reference.ResourcePath + "textures/gui/container/furnace.png");

	private TileEntityEvolvedFurnace tileFurnace;
	private InventoryPlayer invPlayer;

	public GuiFurnaceEvolved(InventoryPlayer playerInv, TileEntityEvolvedFurnace furnaceInv)
	{
		super(new ContainerEvolvedFurnace(playerInv, furnaceInv));
		this.tileFurnace = furnaceInv;
		this.invPlayer = playerInv;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		drawToolTips(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		if (this.tileFurnace.isBurning())
		{
			int k = this.getBurnLeftScaled(13);
			this.drawTexturedModalRect(i + 35, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
		}

		int l = this.getCookProgressScaled(24);
		this.drawTexturedModalRect(i + 58, j + 16, 176, 14, l + 1, 16);

		int m = this.getExperienceScaled(70);
		this.drawTexturedModalRect(i + 89, j + 36, 176, 31, m + 1, 5);
		if (Loader.isModLoaded("NotEnoughItems"))
		{
			this.fontRendererObj.drawString("NEI", 61 + i, 39 + j, Color.gray.getRGB(), false);
		}

	}
	
	protected void drawGuiContainerForegroundLayer(int mouseX, int mousY)
    {
        this.fontRendererObj.drawString(this.tileFurnace.hasCustomInventoryName() ? this.tileFurnace.getInventoryName() : I18n.format(this.tileFurnace.getInventoryName(), new Object[0]), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.invPlayer.hasCustomInventoryName() ? this.invPlayer.getInventoryName() : I18n.format(this.invPlayer.getInventoryName(), new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

	private void drawToolTips(int mouseX, int mouseY)
	{
		int MouseX = mouseX - ((this.width - this.xSize) / 2);
		int MouseY = mouseY - ((this.height - this.ySize) / 2);
		List<String> tooltips = new ArrayList<String>();
		if (MouseX >= 88 && MouseX <= 159 && MouseY >= 35 && MouseY <= 41)
		{
			tooltips.add(this.tileFurnace.getField(4) + " " + StatCollector.translateToLocal("phrase.evolvedfurnace.experiencestored"));
		}
		if (MouseX >= 35 && MouseX <= 50 && MouseY >= 35 && MouseY <= 50)
		{
			tooltips.add(this.getBurnLeftScaled(100) + " %");
		}
		if (MouseX >= 58 && MouseX <= 81 && MouseY >= 16 && MouseY <= 33)
		{
			tooltips.add(this.getCookProgressScaled(100) + " %");
		}
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawHoveringText(tooltips, MouseX + i, MouseY + j, fontRendererObj);
	}

	private int getCookProgressScaled(int pixels)
	{
		int i = this.tileFurnace.getField(2);
		int j = this.tileFurnace.getField(3);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}

	private int getBurnLeftScaled(int pixels)
	{
		int i = this.tileFurnace.getField(1);

		if (i == 0)
		{
			i = 200;
		}

		return (int) (pixels * ((float) this.tileFurnace.getField(0) / (float) i));
	}

	private int getExperienceScaled(int pixels)
	{
		int i = this.tileFurnace.getField(4);
		return (int) (pixels * ((float) i / (float) tileFurnace.maxExperienceStored));
	}

}
