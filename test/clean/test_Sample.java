/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package test;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class Sample
{
	/**
	 *  Main processing method for the Sample object
	 */
	public void run()
	{
		ServerSocket server;

		try
		{
			server = new ServerSocket(1024);
		}
		catch (IOException ioe)
		{
		}

		while (true)
		{
			try
			{
				Socket socket = server.accept();
			}
			catch (IOException ioe)
			{
			}
		}
	}
}
