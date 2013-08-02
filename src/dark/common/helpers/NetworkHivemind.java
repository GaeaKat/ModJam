package dark.common.helpers;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import dark.common.api.IHiveSpire;
import dark.common.prefab.Pos;
import dark.common.prefab.PosWorld;

public class NetworkHivemind
{
    /** Used in the off chance the core location was not set */
    public static Pos backupCoreLocation = new Pos(0, 126, 0);

    private PosWorld hiveCoreLocation;
    private String hiveID = "world";

    /** Machines (Sentries, processors, builders) */
    Set<TileEntity> hiveTiles = new HashSet<TileEntity>();
    /** Entities(Robots) */
    Set<Entity> hiveBots = new HashSet<Entity>();

    Set<IHiveSpire> spires = new HashSet<IHiveSpire>();

    public NetworkHivemind(PosWorld coreLocation, String hiveID, Object... hiveObjects)
    {
        this.hiveCoreLocation = coreLocation;
        this.hiveID = hiveID;
        for (int i = 0; hiveObjects != null && i < hiveObjects.length; i++)
        {
            this.addToHive(hiveObjects[i]);
        }
    }

    public PosWorld getLocation()
    {
        if (this.hiveCoreLocation == null)
        {
            this.hiveCoreLocation = new PosWorld(DimensionManager.getWorld(0), backupCoreLocation);
        }
        return this.hiveCoreLocation;
    }

    public void addToHive(Object obj)
    {
        if (obj instanceof Entity)
        {
            hiveBots.add((Entity) obj);
        }
        if (obj instanceof TileEntity)
        {
            hiveTiles.add((TileEntity) obj);

        }
    }

    public void remove(Object obj)
    {
        if (obj instanceof Entity)
        {
            hiveBots.remove((Entity) obj);
        }
        if (obj instanceof TileEntity)
        {
            hiveTiles.remove((TileEntity) obj);

        }
    }

    public String getID()
    {
        if (this.hiveID == null || this.hiveID.isEmpty())
        {
            this.hiveID = "world";
        }
        return this.hiveID;
    }

    public IHiveSpire getClosestSpire(Object obj)
    {
        IHiveSpire hive = null;
        double distance = Double.MAX_VALUE;
        Pos pos = null;
        World world = null;

        if (obj instanceof Entity)
        {
            pos = new Pos((Entity) obj);
            world = ((Entity) obj).worldObj;
        }
        else if (obj instanceof TileEntity)
        {
            pos = new Pos((TileEntity) obj);
            world = ((TileEntity) obj).worldObj;
        }

        if (pos != null && world != null)
        {
            for (IHiveSpire entry : this.spires)
            {
                double distanceTo = entry.getLocation().getDistanceFrom(pos);
                if (distanceTo < distance)
                {
                    hive = entry;
                    distance = distanceTo;
                }
            }
        }

        return hive;
    }
}
