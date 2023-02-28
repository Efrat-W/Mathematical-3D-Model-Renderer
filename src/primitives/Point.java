package primitives;

public class Point 
{
	final Double3 xy z;
	//add more stuff here perhaps

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
			if (obj instanceof Point other))
			{
				return xyz.equals(other.xyz);
			}
			return false;
	}

	@Override
	public int hashCode() 
	{
		return xyz.hashCode();
	}

	@Override
	public String toString() 
	{
		return "" + xyz;
	}
}
