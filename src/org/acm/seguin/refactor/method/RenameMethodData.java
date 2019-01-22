/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;

import org.acm.seguin.refactor.ComplexTransform;
import org.acm.seguin.summary.MethodSummary;
import org.acm.seguin.summary.PackageSummary;
import org.acm.seguin.summary.Summary;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.query.Ancestor;

/**
 *  Object used to store the state of the rename method visitor
 *
 * @author    Mike Atkinson
 * @since     2.9.11
 */
class RenameMethodData {
	private String oldName;
	private String newName;
	private boolean thisRequired;
	private Summary current;
	private boolean mustInsertThis;
	private TypeSummary typeSummary;
	private boolean canBeFirst;
	private boolean canBeThis;
	private MethodSummary oldMethod;
	private ComplexTransform transform;
	private String fullName;
	private String importedName;


	/**
	 *  Constructor for the RenameFieldData object
	 *
	 *@param  newName   the new field name
	 *@param  oldField  Description of Parameter
	 */
	public RenameMethodData(MethodSummary oldMethod, String newName) {
		this.oldName = oldMethod.getName();
		this.newName = newName;
		this.oldMethod = oldMethod;
		thisRequired = false;
		canBeFirst = false;
		canBeThis = false;
		mustInsertThis = false;
		current = null;
		typeSummary = (TypeSummary) oldMethod.getParent();
		initNames(oldMethod);
	}


	/**
	 *  Constructor for the RenameFieldData object
	 *
	 *@param  newName    the new field name
	 *@param  oldField   Description of Parameter
	 *@param  transform  Description of Parameter
	 */
	public RenameMethodData(MethodSummary oldMethod, String newName,
			ComplexTransform transform) {
		this.newName = newName;
		this.oldMethod = oldMethod;
		this.transform = transform;
		oldName = oldMethod.getName();
		typeSummary = (TypeSummary) oldMethod.getParent();
	}


	/**
	 *  Sets the ThisRequired attribute of the RenameFieldData object
	 *
	 *@param  way  The new ThisRequired value
	 */
	public void setThisRequired(boolean way) {
		thisRequired = way;
	}


	/**
	 *  Sets the CurrentSummary attribute of the RenameFieldData object
	 *
	 *@param  value  The new CurrentSummary value
	 */
	public void setCurrentSummary(Summary value) {
		current = value;
		if (current instanceof TypeSummary) {
			check((TypeSummary) current);
		}
	}


	/**
	 *  Sets the MustInsertThis attribute of the RenameFieldData object
	 *
	 *@param  value  The new MustInsertThis value
	 */
	public void setMustInsertThis(boolean value) {
		mustInsertThis = value;
	}


	/**
	 *  Gets the OldName attribute of the RenameFieldData object
	 *
	 *@return    The OldName value
	 */
	public String getOldName() {
		return oldName;
	}


	/**
	 *  Gets the NewName attribute of the RenameFieldData object
	 *
	 *@return    The NewName value
	 */
	public String getNewName() {
		return newName;
	}


	/**
	 *  Gets the ThisRequired attribute of the RenameFieldData object
	 *
	 *@return    The ThisRequired value
	 */
	public boolean isThisRequired() {
		return thisRequired;
	}


	/**
	 *  Gets the CurrentSummary attribute of the RenameFieldData object
	 *
	 *@return    The CurrentSummary value
	 */
	public Summary getCurrentSummary() {
		return current;
	}


	/**
	 *  Gets the MustInsertThis attribute of the RenameFieldData object
	 *
	 *@return    The MustInsertThis value
	 */
	public boolean isMustInsertThis() {
		return mustInsertThis;
	}


	/**
	 *  Returns the type summary where the field is changing
	 *
	 *@return    The TypeSummary value
	 */
	public TypeSummary getTypeSummary() {
		return typeSummary;
	}


	/**
	 *  Gets the AllowedToChangeFirst attribute of the RenameFieldData object
	 *
	 *@return    The AllowedToChangeFirst value
	 */
	public boolean isAllowedToChangeFirst() {
		return canBeFirst;
	}


	/**
	 *  Gets the AllowedToChangeThis attribute of the RenameFieldData object
	 *
	 *@return    The AllowedToChangeThis value
	 */
	public boolean isAllowedToChangeThis() {
		return canBeThis;
	}


	/**
	 *  Gets the OldField attribute of the RenameFieldData object
	 *
	 *@return    The OldField value
	 */
	public MethodSummary getOldMethod() {
		return oldMethod;
	}


	/**
	 *  Gets the ComplexTransform attribute of the RenameFieldData object
	 *
	 *@return    The ComplexTransform value
	 */
	public ComplexTransform getComplexTransform() {
		return transform;
	}


	/**
	 *  Gets the FullName attribute of the RenameFieldData object
	 *
	 *@return    The FullName value
	 */
	public String getFullName() {
		return fullName;
	}


	/**
	 *  Gets the ImportedName attribute of the RenameFieldData object
	 *
	 *@return    The ImportedName value
	 */
	public String getImportedName() {
		return importedName;
	}


	/**
	 *  Returns true if the system can change the first name in an array
	 *
	 *@param  current  the type summary in question
	 */
	private void check(TypeSummary current) {
		if ((current == typeSummary) || Ancestor.query(current, typeSummary)) {
			canBeFirst = true;
			canBeThis = true;
			return;
		}

		Summary cs = current;
		while (cs != null) {
			if (cs == typeSummary) {
				canBeThis = false;
				canBeFirst = true;
				return;
			}

			cs = cs.getParent();
		}

		canBeThis = false;
		canBeFirst = false;
	}


	/**
	 *  Initialize the names
	 *
	 *@param  field  the field summary
	 */
	private void initNames(MethodSummary method) {
		StringBuffer buffer = new StringBuffer(method.getName());

		Summary current = method;
		while (current != null) {
			if (current instanceof TypeSummary) {
				buffer.insert(0, ".");
				buffer.insert(0, current.getName());
			}

			if (current instanceof PackageSummary) {
				importedName = buffer.toString();
				buffer.insert(0, ".");
				buffer.insert(0, current.getName());
				fullName = buffer.toString();
				return;
			}

			current = current.getParent();
		}

		//  We should never get here
		importedName = buffer.toString();
		fullName = buffer.toString();
	}
}

