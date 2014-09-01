package tsuteto.tofu.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.Level;
import tsuteto.tofu.item.iteminfo.SoymilkPlayerInfo;
import tsuteto.tofu.network.AbstractPacket;
import tsuteto.tofu.util.ModLog;

import java.io.IOException;

public class PacketSoymilkInfo extends AbstractPacket
{
    private NBTTagCompound rootNBT;

    public PacketSoymilkInfo() {}

    public PacketSoymilkInfo(SoymilkPlayerInfo info)
    {
        rootNBT = new NBTTagCompound();
        info.writeNBTTo(rootNBT);
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        PacketBuffer packet = new PacketBuffer(buffer);

        try
        {
            packet.writeNBTTagCompoundToBuffer(rootNBT);
        }
        catch (IOException e)
        {
            ModLog.log(Level.WARN, e, "Failed to send NBT tag!");
        }
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        PacketBuffer packet = new PacketBuffer(buffer);
        try
        {
            rootNBT = packet.readNBTTagCompoundFromBuffer();
        }
        catch (IOException e)
        {
            ModLog.log(Level.WARN, e, "Failed to receive NBT tag!");
        }
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        SoymilkPlayerInfo info = SoymilkPlayerInfo.of(player).readNBTFrom(rootNBT);
        info.writeNBTToPlayer();
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {

    }
}
