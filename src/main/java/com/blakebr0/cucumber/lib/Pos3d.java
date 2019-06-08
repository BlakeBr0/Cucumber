package com.blakebr0.cucumber.lib;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

/**
 * Pos3D - a way of performing operations on objects in a three dimensional environment.
 *
 * @author aidancbrady
 */
public class Pos3d extends Vec3d {
	public Pos3d() {
		this(0, 0, 0);
	}

	public Pos3d(Vec3d vec) {
		super(vec.x, vec.y, vec.z);
	}

	public Pos3d(Vec3i vec) {
		super(vec);
	}

	public Pos3d(RayTraceResult mop) {
		this(mop.getHitVec());
	}

	public Pos3d(double x, double y, double z) {
		super(x, y, z);
	}

	/**
	 * Creates a Pos3D with an entity's posX, posY, and posZ values.
	 *
	 * @param entity - entity to create the Pos3D from
	 */
	public Pos3d(Entity entity) {
		this(entity.posX, entity.posY, entity.posZ);
	}

	/**
	 * Creates a Pos3D with a TileEntity's x, y and z values.
	 *
	 * @param tileEntity - TileEntity to create the Pos3D from
	 */
	public Pos3d(TileEntity tileEntity) {
		this(tileEntity.getPos());
	}

	/**
	 * Returns a new Pos3D from a tag compound.
	 *
	 * @param tag - tag compound to read from
	 * @return the Pos3D from the tag compound
	 */
	public static Pos3d read(CompoundNBT tag) {
		return new Pos3d(tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z"));
	}

	/**
	 * Writes this Pos3D's data to an NBTTagCompound.
	 *
	 * @param tag - tag compound to write to
	 * @return the tag compound with this Pos3D's data
	 */
	public CompoundNBT write(CompoundNBT tag) {
		tag.putDouble("x", x);
		tag.putDouble("y", y);
		tag.putDouble("z", z);

		return tag;
	}

	/**
	 * Creates and returns a Pos3D with values representing the difference between this and the Pos3D in the parameters.
	 *
	 * @param vec - Vec3 to subtract
	 * @return difference of the two Pos3Ds
	 */
	public Pos3d diff(Vec3d vec) {
		return new Pos3d(x - vec.x, y - vec.y, z - vec.z);
	}

	/**
	 * Creates a new Pos3D from the motion of an entity.
	 *
	 * @param entity Entity to get the motion from
	 * @return Pos3D representing the motion of the given entity
	 */
	public static Pos3d fromMotion(Entity entity) {
		return new Pos3d(entity.getMotion().getX(), entity.getMotion().getY(), entity.getMotion().getZ());
	}

	/**
	 * Centres a block-derived Pos3D
	 */
	public Pos3d centre() {
		return translate(0.5, 0.5, 0.5);
	}

	/**
	 * Translates this Pos3D by the defined values.
	 *
	 * @param x - amount to translate on the x axis
	 * @param y - amount to translate on the y axis
	 * @param z - amount to translate on the z axis
	 * @return the translated Pos3D
	 */
	public Pos3d translate(double x, double y, double z) {
		return new Pos3d(this.x + x, this.y + y, this.z + z);
	}

	/**
	 * Performs the same operation as translate(x, y, z), but with a Pos3D value instead.
	 *
	 * @param pos - Pos3D value to translate by
	 * @return translated Pos3D
	 */
	public Pos3d translate(Vec3d pos) {
		return translate(pos.x, pos.y, pos.z);
	}

	/**
	 * Performs the same operation as translate(x, y, z), but by a set amount in a EnumFacing
	 */
	public Pos3d translate(Direction direction, double amount) {
		return translate(direction.getDirectionVec().getX() * amount, direction.getDirectionVec().getY() * amount, direction.getDirectionVec().getZ() * amount);
	}

	/**
	 * Performs the same operation as translate(x, y, z), but by a set amount in a EnumFacing
	 */
	public Pos3d translateExcludingSide(Direction direction, double amount) {
		double xPos = x, yPos = y, zPos = z;
		if (direction.getAxis() != Direction.Axis.X) xPos += amount;
		if (direction.getAxis() != Direction.Axis.Y) yPos += amount;
		if (direction.getAxis() != Direction.Axis.Z) zPos += amount;

		return new Pos3d(xPos, yPos, zPos);
	}

	/**
	 * Returns the distance between this and the defined Pos3D.
	 *
	 * @param pos - the Pos3D to find the distance to
	 * @return the distance between this and the defined Pos3D
	 */
	public double distance(Vec3d pos) {
		double subX = x - pos.x;
		double subY = y - pos.y;
		double subZ = z - pos.z;
		return MathHelper.sqrt(subX * subX + subY * subY + subZ * subZ);
	}

	/**
	 * Rotates this Pos3D by the defined yaw value.
	 *
	 * @param yaw - yaw to rotate by
	 * @return rotated Pos3D
	 */
	public Pos3d rotateYaw(float yaw) {
		double yawRadians = Math.toRadians(yaw);

		double xPos = x;
		double zPos = z;

		if (yaw != 0) {
			xPos = x * Math.cos(yawRadians) - z * Math.sin(yawRadians);
			zPos = z * Math.cos(yawRadians) + x * Math.sin(yawRadians);
		}

		return new Pos3d(xPos, y, zPos);
	}

	@Override
	public Pos3d rotatePitch(float pitch) {
		double pitchRadians = Math.toRadians(pitch);

		double yPos = y;
		double zPos = z;

		if (pitch != 0) {
			yPos = y * Math.cos(pitchRadians) - z * Math.sin(pitchRadians);
			zPos = z * Math.cos(pitchRadians) + y * Math.sin(pitchRadians);
		}

		return new Pos3d(x, yPos, zPos);
	}

	public Pos3d rotate(float yaw, float pitch) {
		return rotate(yaw, pitch, 0);
	}

	public Pos3d rotate(float yaw, float pitch, float roll) {
		double yawRadians = Math.toRadians(yaw);
		double pitchRadians = Math.toRadians(pitch);
		double rollRadians = Math.toRadians(roll);

		double xPos = x * Math.cos(yawRadians) * Math.cos(pitchRadians) + z * (Math.cos(yawRadians) * Math.sin(pitchRadians) * Math.sin(rollRadians) - Math.sin(yawRadians) * Math.cos(rollRadians)) + y * (Math.cos(yawRadians) * Math.sin(pitchRadians) * Math.cos(rollRadians) + Math.sin(yawRadians) * Math.sin(rollRadians));
		double zPos = x * Math.sin(yawRadians) * Math.cos(pitchRadians) + z * (Math.sin(yawRadians) * Math.sin(pitchRadians) * Math.sin(rollRadians) + Math.cos(yawRadians) * Math.cos(rollRadians)) + y * (Math.sin(yawRadians) * Math.sin(pitchRadians) * Math.cos(rollRadians) - Math.cos(yawRadians) * Math.sin(rollRadians));
		double yPos = -x * Math.sin(pitchRadians) + z * Math.cos(pitchRadians) * Math.sin(rollRadians) + y * Math.cos(pitchRadians) * Math.cos(rollRadians);

		return new Pos3d(xPos, yPos, zPos);
	}

	public Pos3d multiply(Vec3d pos) {
		return scale(pos.x, pos.y, pos.z);
	}

	/**
	 * Scales this Pos3D by the defined x, y, an z values.
	 *
	 * @param x - x value to scale by
	 * @param y - y value to scale by
	 * @param z - z value to scale by
	 * @return scaled Pos3D
	 */
	public Pos3d scale(double x, double y, double z) {
		return new Pos3d(x * x, y * y, z * z);
	}

	@Override
	public Pos3d scale(double scale) {
		return scale(scale, scale, scale);
	}

	public Pos3d rotate(float angle, Pos3d axis) {
		return translateMatrix(getRotationMatrix(angle, axis), this);
	}

	public double[] getRotationMatrix(float angle) {
		double[] matrix = new double[16];
		Pos3d axis = clone().normalize();

		double x = axis.x;
		double y = axis.y;
		double z = axis.z;

		angle *= 0.0174532925D;

		float cos = (float) Math.cos(angle);
		float ocos = 1.0F - cos;
		float sin = (float) Math.sin(angle);

		matrix[0] = (x * x * ocos + cos);
		matrix[1] = (y * x * ocos + z * sin);
		matrix[2] = (x * z * ocos - y * sin);
		matrix[4] = (x * y * ocos - z * sin);
		matrix[5] = (y * y * ocos + cos);
		matrix[6] = (y * z * ocos + x * sin);
		matrix[8] = (x * z * ocos + y * sin);
		matrix[9] = (y * z * ocos - x * sin);
		matrix[10] = (z * z * ocos + cos);
		matrix[15] = 1.0F;

		return matrix;
	}

	public static Pos3d translateMatrix(double[] matrix, Pos3d translation) {
		double x = translation.x * matrix[0] + translation.y * matrix[1] + translation.z * matrix[2] + matrix[3];
		double y = translation.x * matrix[4] + translation.y * matrix[5] + translation.z * matrix[6] + matrix[7];
		double z = translation.x * matrix[8] + translation.y * matrix[9] + translation.z * matrix[10] + matrix[11];

		return new Pos3d(x, y, z);
	}

	public static double[] getRotationMatrix(float angle, Pos3d axis) {
		return axis.getRotationMatrix(angle);
	}

	public double anglePreNorm(Pos3d pos2) {
		return Math.acos(dotProduct(pos2));
	}

	public static double anglePreNorm(Pos3d pos1, Pos3d pos2) {
		return Math.acos(pos1.clone().dotProduct(pos2));
	}

	@Override
	public Pos3d normalize() {
		return new Pos3d(super.normalize());
	}

	public Pos3d xCrossProduct() {
		return new Pos3d(0.0D, z, -y);
	}

	public Pos3d zCrossProduct() {
		return new Pos3d(-y, x, 0.0D);
	}

	public Pos3d getPerpendicular() {
		if (z == 0) {
			return zCrossProduct();
		}

		return xCrossProduct();
	}

	public Pos3d floor() {
		return new Pos3d(Math.floor(x), Math.floor(y), Math.floor(z));
	}

	public static AxisAlignedBB getAABB(Pos3d pos1, Pos3d pos2) {
		return new AxisAlignedBB(
				pos1.x,
				pos1.y,
				pos1.z,
				pos2.x,
				pos2.y,
				pos2.z
		);
	}

	@Override
	public Pos3d clone() {
		return new Pos3d(x, y, z);
	}

	@Override
	public String toString() {
		return "[Pos3D: " + x + ", " + y + ", " + z + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Vec3d &&
				((Vec3d) obj).x == x &&
				((Vec3d) obj).x == y &&
				((Vec3d) obj).x == z;
	}

	@Override
	public int hashCode() {
		int code = 1;
		code = 31 * code + new Double(x).hashCode();
		code = 31 * code + new Double(y).hashCode();
		code = 31 * code + new Double(z).hashCode();
		return code;
	}
}