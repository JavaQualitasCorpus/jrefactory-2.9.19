/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.refactor.field;

import org.acm.seguin.refactor.ComplexTransform;
import org.acm.seguin.summary.FieldSummary;
import org.acm.seguin.summary.PackageSummary;
import org.acm.seguin.summary.Summary;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.query.Ancestor;



/**
 *  Object used to store the state of the rename field visitor
 *
 * @author    Chris Seguin
 * @since     2.4.0
 */
class RenameFieldData {
   private String oldName;
   private String newName;
   private boolean thisRequired;
   private Summary current;
   private boolean mustInsertThis;
   private TypeSummary typeSummary;
   private boolean canBeFirst;
   private boolean canBeThis;
   private FieldSummary oldField;
   private ComplexTransform transform;
   private String fullName;
   private String importedName;


   /**
    *  Constructor for the RenameFieldData object
    *
    * @param  oldField  Description of Parameter
    * @param  newName   the new field name
    * @since            2.4.0
    */
   public RenameFieldData(FieldSummary oldField, String newName) {
      this.oldName = oldField.getName();
      this.newName = newName;
      this.oldField = oldField;
      thisRequired = false;
      canBeFirst = false;
      canBeThis = false;
      mustInsertThis = false;
      current = null;
      typeSummary = (TypeSummary)oldField.getParent();
      initNames(oldField);
   }


   /**
    *  Constructor for the RenameFieldData object
    *
    * @param  oldField   Description of Parameter
    * @param  newName    the new field name
    * @param  transform  Description of Parameter
    * @since             2.4.0
    */
   public RenameFieldData(FieldSummary oldField, String newName,
                          ComplexTransform transform) {
      this.newName = newName;
      this.oldField = oldField;
      this.transform = transform;
      oldName = oldField.getName();
      typeSummary = (TypeSummary)oldField.getParent();
   }


   /**
    *  Sets the ThisRequired attribute of the RenameFieldData object
    *
    * @param  way  The new ThisRequired value
    * @since       2.4.0
    */
   public void setThisRequired(boolean way) {
      thisRequired = way;
   }


   /**
    *  Sets the CurrentSummary attribute of the RenameFieldData object
    *
    * @param  value  The new CurrentSummary value
    * @since         2.4.0
    */
   public void setCurrentSummary(Summary value) {
      current = value;
      if (current instanceof TypeSummary) {
         check((TypeSummary)current);
      }
   }


   /**
    *  Sets the MustInsertThis attribute of the RenameFieldData object
    *
    * @param  value  The new MustInsertThis value
    * @since         2.4.0
    */
   public void setMustInsertThis(boolean value) {
      mustInsertThis = value;
   }


   /**
    *  Gets the OldName attribute of the RenameFieldData object
    *
    * @return    The OldName value
    * @since     2.4.0
    */
   public String getOldName() {
      return oldName;
   }


   /**
    *  Gets the NewName attribute of the RenameFieldData object
    *
    * @return    The NewName value
    * @since     2.4.0
    */
   public String getNewName() {
      return newName;
   }


   /**
    *  Gets the ThisRequired attribute of the RenameFieldData object
    *
    * @return    The ThisRequired value
    * @since     2.4.0
    */
   public boolean isThisRequired() {
      return thisRequired;
   }


   /**
    *  Gets the CurrentSummary attribute of the RenameFieldData object
    *
    * @return    The CurrentSummary value
    * @since     2.4.0
    */
   public Summary getCurrentSummary() {
      return current;
   }


   /**
    *  Gets the MustInsertThis attribute of the RenameFieldData object
    *
    * @return    The MustInsertThis value
    * @since     2.4.0
    */
   public boolean isMustInsertThis() {
      return mustInsertThis;
   }


   /**
    *  Returns the type summary where the field is changing
    *
    * @return    The TypeSummary value
    * @since     2.4.0
    */
   public TypeSummary getTypeSummary() {
      return typeSummary;
   }


   /**
    *  Gets the AllowedToChangeFirst attribute of the RenameFieldData object
    *
    * @return    The AllowedToChangeFirst value
    * @since     2.4.0
    */
   public boolean isAllowedToChangeFirst() {
      return canBeFirst;
   }


   /**
    *  Gets the AllowedToChangeThis attribute of the RenameFieldData object
    *
    * @return    The AllowedToChangeThis value
    * @since     2.4.0
    */
   public boolean isAllowedToChangeThis() {
      return canBeThis;
   }


   /**
    *  Gets the OldField attribute of the RenameFieldData object
    *
    * @return    The OldField value
    * @since     2.4.0
    */
   public FieldSummary getOldField() {
      return oldField;
   }


   /**
    *  Gets the ComplexTransform attribute of the RenameFieldData object
    *
    * @return    The ComplexTransform value
    * @since     2.4.0
    */
   public ComplexTransform getComplexTransform() {
      return transform;
   }


   /**
    *  Gets the FullName attribute of the RenameFieldData object
    *
    * @return    The FullName value
    * @since     2.4.0
    */
   public String getFullName() {
      return fullName;
   }


   /**
    *  Gets the ImportedName attribute of the RenameFieldData object
    *
    * @return    The ImportedName value
    * @since     2.4.0
    */
   public String getImportedName() {
      return importedName;
   }


   /**
    *  Converts to a String representation of the object.
    *
    * @return    A string representation of the object.
    * @since     2.4.0
    */
   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("  oldName=").append(oldName);
      sb.append("\n  newName=").append(newName);
      sb.append("\n  thisRequired=").append(thisRequired);
      sb.append("\n  current=").append(current);
      sb.append("\n  mustInsertThis=").append(mustInsertThis);
      sb.append("\n  typeSummary=").append(typeSummary);
      sb.append("\n  canBeFirst=").append(canBeFirst);
      sb.append("\n  canBeThis=").append(canBeThis);
      sb.append("\n  oldField=").append(oldField);
      sb.append("\n  transform=").append(transform);
      sb.append("\n  fullName=").append(fullName);
      sb.append("\n  importedName=").append(importedName).append("\n");
      return sb.toString();
   }


   /**
    *  Returns true if the system can change the first name in an array
    *
    * @param  current  the type summary in question
    * @since           2.4.0
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
    * @param  field  the field summary
    * @since         2.4.0
    */
   private void initNames(FieldSummary field) {
      StringBuffer buffer = new StringBuffer(field.getName());

      Summary current = field;
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

