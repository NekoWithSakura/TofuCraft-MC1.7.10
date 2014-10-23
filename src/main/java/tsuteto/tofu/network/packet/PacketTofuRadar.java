package tsuteto.tofu.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.item.ItemTofuSlimeRadar;
import tsuteto.tofu.item.TcItems;
import tsuteto.tofu.network.AbstractPacket;
import tsuteto.tofu.network.MessageToClient;

public class PacketTofuRadar extends AbstractPacket implements MessageToClient
{
    boolean isSpawnChunk;

    public PacketTofuRadar() {}

    public PacketTofuRadar(boolean isSpawnChunk)
    {
        this.isSpawnChunk = isSpawnChunk;
    }

    @Override
    public void encodeInto(ByteBuf buffer)
    {
        buffer.writeBoolean(isSpawnChunk);
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        isSpawnChunk = buffer.readBoolean();
    }

    @Override
    public IMessage handleClientSide(EntityPlayer player)
    {
        ItemStack itemstack = player.getCurrentEquippedItem();

        if (itemstack != null && itemstack.getItem() == TcItems.tofuRadar)
        {
            ((ItemTofuSlimeRadar)TcItems.tofuRadar).onUse(isSpawnChunk, itemstack, player.worldObj, player);
        }
        return null;
    }
}
