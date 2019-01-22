package msn.messages.commands;

import javax.swing.*;

import msn.*;
import msn.messages.*;
import msn.util.*;

/**
 * (ADD) Adds a user to a list.
 *
 * @author     mace
 * @created    June 22, 2003
 * @modified   $Date: 2003/07/18 16:09:12 $ by $Author: mikeatkinson $
 * @version    $Revision: 1.1 $ modified $Date: 2003/07/18 16:09:12 $ by $Author: mikeatkinson $
 * @since      MSNP2
 */
public class JEditBug_746217 extends StatefulCommand {
	private Contact contact;

	public JEditBug_746217(MessagingClient client, String list, Contact contact) {
		super(client, "ADD", list + " " + contact.getUserName() + " " + contact.getNickName());
		this.contact = contact;
	}

	public void processResponse(String[] parts) {
		String cmd = parts[0];
		if (cmd.equals("ADD")) {
			processAdd(parts);
		} else if (cmd.equals("ILN")) {
			processInitialStatus(parts);
		} else {
			unknown(parts);
		}
	}

	//{{{ processAdd()
	/**
	 * (ADD) notice/confirmation that a user was added to a list.
	 *
	 * <code>ADD 21 {FL|RL|AL|BL} listVersion example@passport.com example@passport.com</code>
	 *
	 * @param parts  Description of the Parameter
	 * @since        MSNP2
	 */
	protected void processAdd(String[] parts) {
		String list = parts[2];
		ContactList cl;
		Contact contact = new Contact(parts[4], MSNUtilities.decode(parts[5]));
		if (list.equals("FL")) {
			cl = MSN.getInstance().getContactList();
		} else if (list.equals("RL")) {
			if (MSN.getBooleanProperty(PropertyManager.REVERSE_LIST_PROMPTING, false)) {
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Do you want to allow this user to send you messages?", contact + " added you", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
					client.send(new AddUser(client, "AL", contact));
				} else {
					client.send(new AddUser(client, "BL", contact));
				}
			} else {
				client.send(new AddUser(client, "AL", contact));
			}
			cl = MSN.getInstance().getReverseList();
		} else if (list.equals("AL")) {
			cl = MSN.getInstance().getAllowedList();
		} else if (list.equals("BL")) {
			cl = MSN.getInstance().getBlockedList();
		} else {
			unknown(parts);
			return;
		}
		cl.addContact(contact);
		cl.sort();
	}//}}}

	//{{{ processInitialStatus()
	/**
	 * (ILN) A notification of the state of contacts when you first sign on.
	 * One for each contact online is returned after your initial online status is set.
	 * <P>
	 * <code>ILN TrID {NLN|HDN|BSY|AWY|IDL|PHN|BRB|LUN} username nickname</code>
	 * ex: <code>ILN 7 NLN bemace@hotmail.com Brad</code>
	 *
	 * @param parts  Description of the Parameter
	 * @since        MSNP2
	 */
	protected void processInitialStatus(String[] parts) {
		Contact c = MSN.getInstance().getContactList().getContact(parts[3]);
		c.setStatus(parts[2]);
		c.setNickName(MSNUtilities.decode(parts[4]));
		c.notifyObservers();
	}//}}}

	//{{{ processError()
	protected void processError(int error, int transactionID) {
		switch (error) {
			case MSNConstants.ERR_INVALID_PARAMETER:
				System.out.println("Invalid username: " + contact.getUserName());
				break;
			default:
				System.out.println("Error: " + error);
		}
	}//}}}
}

