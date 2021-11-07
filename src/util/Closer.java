package util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Closer
{
	public static void close(InputStream o)
	{
		if (o == null) return;
		try
		{
			o.close();
		}
		catch (Exception e)
		{
		}
	}

	public static void close(OutputStream o)
	{
		if (o == null) return;
		try
		{
			o.close();
		}
		catch (Exception e)
		{
		}
	}

	public static void close(Reader o)
	{
		if (o == null) return;
		try
		{
			o.close();
		}
		catch (Exception e)
		{
		}
	}

	public static void close(Writer o)
	{
		if (o == null) return;
		try
		{
			o.close();
		}
		catch (Exception e)
		{
		}
	}

	public static void close(Connection o)
	{
		if (o == null) return;
		try
		{
			o.close();
		}
		catch (Exception e)
		{
		}
	}

	public static void close(Statement o)
	{
		if (o == null) return;
		try
		{
			o.close();
		}
		catch (Exception e)
		{
		}
	}

	public static void close(ResultSet o)
	{
		if (o == null) return;
		try
		{
			o.close();
		}
		catch (Exception e)
		{
		}
	}

}
