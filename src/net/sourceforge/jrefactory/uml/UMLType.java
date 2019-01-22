package net.sourceforge.jrefactory.uml;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.util.*;
import javax.swing.*;
import net.sourceforge.jrefactory.ast.ModifierHolder;
import net.sourceforge.jrefactory.uml.line.*;
import org.acm.seguin.summary.*;



/**
 *  Description of the Class
 *
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: UMLType.java,v 1.2 2004/05/04 15:41:36 mikeatkinson Exp $
 */
public class UMLType extends EndPointPanel implements HasSummary {

   private int state = DEFAULT;
   private TypeSummary typeSummary;
   private UMLSettings settings;
   private RoleHolder roles;
   private boolean viewPrivate = true;

   // states
   private final static int DEFAULT = 0;
   private final static int FOREIGN = 2;
   private final static int SELECTED = 1;
   private final static int FOREIGN_SELECTED = 3;

   // layout constants
   private final static int X_OFFSET = 5;
   private final static int TOP_OFFSET = 4;
   private final static int BOTTOM_OFFSET = 2;
   private final static int borderWidth = 8;


   /**
    *  Constructor for the UMLType object
    *
    * @param  pack      Description of Parameter
    * @param  summary   Description of Parameter
    * @param  settings  Description of Parameter
    * @param  foreign   Description of Parameter
    * @since            2.9.12
    */
   public UMLType(UMLPackage pack, TypeSummary summary, UMLSettings settings, boolean foreign) {
      super(new TypeLayout());
      this.typeSummary = summary;
      this.settings = settings;
      setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
      if (foreign) {
         state = FOREIGN;
      }
      setBackground(settings.getBackgroundColor(state));

      //  Create another adapter for draging this

      //  Add the name label
      JLabel nameLabel = new JLabel(typeSummary.getName(), JLabel.CENTER);
      nameLabel.setLocation(borderWidth, borderWidth);
      nameLabel.setFont(settings.getProtectionFont(true, typeSummary));
      add(nameLabel);

      //  Check to see if we need a role
      roles = new RoleHolder();
      if (typeSummary.isInterface()) {
         roles.add(((char)171) + "Interface" + ((char)187));
      }
      if (foreign) {
         roles.add("Package:  " + getPackageName());
      }

      if (roles.hasAny()) {
         add(roles);
      }

      add(new Divider());
      for (Iterator fields = summary.getFields(); fields != null && fields.hasNext(); ) {
         add(UMLFactory.createField((FieldSummary)fields.next(), settings));
      }
      add(new Divider());
      for (Iterator methods = summary.getMethods(); methods != null && methods.hasNext(); ) {
         MethodSummary method = (MethodSummary)methods.next();
         if (!method.isInitializer()) {
            add(UMLFactory.createMethod(method, settings));
         }
      }
      if (summary.getTypes() != null) {
         add(new Divider());
      }
      UMLSettings typeSettings = new UMLSettings(settings);
      typeSettings.setAsLine(true);
      for (Iterator types = summary.getTypes(); types != null && types.hasNext(); ) {
         add(UMLFactory.createNestedType((TypeSummary)types.next(), typeSettings));
      }

      //find maximum component width
      int width = 50;
      double scale = pack.getScale();
      for (int i = 0; i < getComponentCount(); i++) {
         Component c = getComponent(i);
         if (c.isVisible()) {
            Dimension d = c.getPreferredSize();
            int xinc = 0;
            if (c instanceof JLabel) {
               xinc = (int)(((JLabel)c).getText().length() * scale);
            }
            if (c instanceof RoleHolder) {
               xinc = (int)(((RoleHolder)c).maxRoleWidth() * scale);
            }
            if (d.width + xinc > width) {
               width = d.width + xinc;
            }
         }
      }
      Dimension d = nameLabel.getPreferredSize();
      d.width = width;
      nameLabel.setPreferredSize(d);
      roles.resetWidth(width);
   }

   /**
    *  Sets the viewPrivate attribute of the UMLType object
    *
    * @param  view  The new viewPrivate value
    * @since        2.9.12
    */
   public void setViewPrivate(boolean view) {
      if (viewPrivate != view) {
         viewPrivate = view;
         for (int i = 0; i < getComponentCount(); i++) {
            Component c = getComponent(i);
            if (c instanceof HasSummary) {
               Summary summary = ((HasSummary)c).getSourceSummary();
               if (summary instanceof ModifierHolder) {
                  if (((ModifierHolder)summary).isPrivate()) {
                     c.setVisible(view);
                  }
               }
            }
         }
      }
   }


   /**
    *  Sets the Selected attribute of the UMLType object
    *
    * @param  way  The new Selected value
    * @since       2.9.12
    */
   public void setSelected(boolean way) {
      if (way) {
         select();
      } else {
         deselect();
      }
   }


   /**
    *  Sets the foreign attribute of the UMLType object
    *
    * @param  foreign  The new foreign value
    * @since           2.9.12
    */
   public void setForeign(boolean foreign) {
      state = state | FOREIGN;
      setBackground(UMLSettings.getBackgroundColor(state));
      getParent().repaint();
   }


   /**
    *  Gets the viewPrivate attribute of the UMLType object
    *
    * @return    The viewPrivate value
    * @since     2.9.12
    */
   public boolean isViewPrivate() {
      return viewPrivate;
   }



   /**
    *  Get the Summary for this object.
    *
    * @return    the summary
    * @since     2.9.12
    */
   public Summary getSourceSummary() {
      return typeSummary;
   }


   /**
    *  Returns an identifier for a type
    *
    * @return    an identifier for this panel
    * @since     2.9.12
    */
   public String getID() {
      return typeSummary.getPackageSummary().getName() + ":" + typeSummary.getName();
   }


   /**
    *  Gets the selected attribute of the UMLType object
    *
    * @return    The selected value
    * @since     2.9.12
    */
   public boolean isSelected() {
      return (state & 1) == 1;
   }


   /**
    *  Gets the foreign attribute of the UMLType object
    *
    * @return    The foreign value
    * @since     2.9.12
    */
   public boolean isForeign() {
      return (state & FOREIGN) == FOREIGN;
   }


   /**
    *  Gets the summary attribute of the UMLType object
    *
    * @return    The summary value
    * @since     2.9.12
    */
   public TypeSummary getSummary() {
      return typeSummary;
   }


   /**
    *  Count the number of attributes
    *
    * @param  name  Description of Parameter
    * @return       the number of attributes
    * @since        2.9.12
    */
   public UMLField getField(String name) {
      if (name == null) {
         return null;
      }

      Component[] children = getComponents();
      for (int ndx = 0; ndx < children.length; ndx++) {
         if (children[ndx] instanceof UMLField) {
            UMLField field = (UMLField)children[ndx];
            if (name.equals(field.getSummary().getName())) {
               return field;
            }
         }
      }
      return null;  // Not found
   }


   /**
    *  Description of the Method
    *
    * @param  g  Description of Parameter
    * @since     2.9.12
    */
   public void paint(Graphics g) {
      super.paint(g);
      Component[] components = getComponents();
      Insets insets = getInsets();
      int y = TOP_OFFSET;
      for (int i = 0; i < components.length; i++) {
         if (components[i].isVisible()) {
            if (components[i] instanceof Divider) {
               g.drawLine(-insets.left, y + 2, 1000 + insets.right - 1, y + 2);
            }
            Dimension d = components[i].getSize();
            y += d.height;
         }
      }
   }


   /**
    *  Load the type
    *
    * @param  buffer  the buffer
    * @since          2.9.12
    */
   public void load(String buffer) {
      StringTokenizer tok = new StringTokenizer(buffer, ",");
      String strX = tok.nextToken();
      String strY = tok.nextToken();

      try {
         setLocation(Integer.parseInt(strX), Integer.parseInt(strY));
      } catch (NumberFormatException nfe) {
      }
   }


   /**
    *  Select this item
    *
    * @since    2.9.12
    */
   public void select() {
      state = state | SELECTED;
      setBackground(UMLSettings.getBackgroundColor(state));
      getParent().repaint();
   }


   /**
    *  Select this item
    *
    * @since    2.9.12
    */
   public void deselect() {
      state = state & ~SELECTED;
      setBackground(UMLSettings.getBackgroundColor(state));
      getParent().repaint();
   }


   /**
    *  Toggle the selected state
    *
    * @since    2.9.12
    */
   public void toggleSelect() {
      state = state ^ SELECTED;
      setBackground(UMLSettings.getBackgroundColor(state));
      getParent().repaint();
   }


   /**
    *  This method moves the class diagram around on the screen
    *
    * @param  x  the x coordinate (scaled value)
    * @param  y  the y coordinate (scaled value)
    * @since     2.9.12
    */
   public void shift(int x, int y) {
      Point pt = getLocation();
      setLocation(Math.max(0, x + pt.x), Math.max(0, y + pt.y));
   }




   /**
    *  Convert an attribute to an association
    *
    * @param  packagePanel  the package panel
    * @param  fieldPanel    the field panel
    * @return               the new segmented line
    * @since                2.9.12
    */
   public AssociationRelationship convertToAssociation(UMLPackage packagePanel, UMLField fieldPanel) {
      remove(fieldPanel);
      packagePanel.add(fieldPanel);

      TypeSummary typeSummary = fieldPanel.getType();
      UMLType endPanel = packagePanel.findType(typeSummary);
      if (endPanel == null) {
         endPanel = new UMLType(packagePanel, typeSummary, settings, true);
         packagePanel.add(endPanel);
         endPanel.setLocation(0, 0);
      }

      AssociationRelationship result = new AssociationRelationship(this, (EndPointPanel)endPanel, fieldPanel);
      packagePanel.add(result);

      return result;
   }


   /**
    *  Convert from an association to an attribute
    *
    * @param  packagePanel  the package panel
    * @param  fieldPanel    the field panel
    * @since                2.9.12
    */
   public void convertToAttribute(UMLPackage packagePanel, UMLField fieldPanel) {
      packagePanel.remove(fieldPanel);
      packagePanel.removeAssociation(fieldPanel);
      add(fieldPanel);
   }


   /**
    *  Save the files
    *
    * @param  output  the output stream
    * @since          2.9.12
    */
   public void save(PrintWriter output) {
      Point pt = getLocation();
      output.println("P[" + getID() + "]{" + pt.x + "," + pt.y + "," + isVisible() + "," + viewPrivate + "}");
   }


   /**
    *  Adds the specified Mouse listener to receive Mouse events from this component. If listener l is null, no
    *  exception is thrown and no action is performed.
    *
    * @param  l  Contains the MouseListener for MouseEvent data.
    * @since     2.9.12
    */
   public void addMouseListener(MouseListener l) {
      super.addMouseListener(l);
      Component[] children = getComponents();
      for (int i = 0; i < children.length; i++) {
         children[i].addMouseListener(l);
      }
   }


   /**
    *  Adds the specified MouseMotion listener to receive MouseMotion events from this component. If listener l is null,
    *  no exception is thrown and no action is performed.
    *
    * @param  l  Contains the MouseMotionListener for MouseMotionEvent data.
    * @since     2.9.12
    */
   public void addMouseMotionListener(MouseMotionListener l) {
      super.addMouseMotionListener(l);
      Component[] children = getComponents();
      for (int i = 0; i < children.length; i++) {
         children[i].addMouseMotionListener(l);
      }
   }


   /**
    *  Removes the specified Mouse listener so that it no longer receives Mouse events from this component. This method
    *  performs no function, nor does it throw an exception, if the listener specified by the argument was not
    *  previously added to this component. If listener l is null, no exception is thrown and no action is performed.
    *
    * @param  l  Contains the MouseListener for MouseEvent data.
    * @since     2.9.12
    */
   public void removeMouseListener(MouseListener l) {
      super.removeMouseListener(l);
      Component[] children = getComponents();
      for (int i = 0; i < children.length; i++) {
         children[i].removeMouseListener(l);
      }
   }


   /**
    *  Get the name of the package
    *
    * @return    the package name
    * @since     2.9.12
    */
   private String getPackageName() {
      Summary current = typeSummary;
      while (!(current instanceof PackageSummary)) {
         current = current.getParent();
      }

      return ((PackageSummary)current).getName();
   }


   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    * @since     2.9.12
    */
   private static class Divider extends JComponent {
      Dimension size = new Dimension(10, 5);


      /**
       *  Gets the minimumSize attribute of the Divider object
       *
       * @return    The minimumSize value
       * @since     2.9.12
       */
      public Dimension getMinimumSize() {
         return size;
      }


      /**
       *  Gets the maximumSize attribute of the Divider object
       *
       * @return    The maximumSize value
       * @since     2.9.12
       */
      public Dimension getMaximumSize() {
         return size;
      }


      /**
       *  Gets the preferredSize attribute of the Divider object
       *
       * @return    The preferredSize value
       * @since     2.9.12
       */
      public Dimension getPreferredSize() {
         return size;
      }
   }


   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    * @since     2.9.12
    */
   private static class TypeLayout implements LayoutManager2 {
      Dimension minSize = new Dimension(10, 10);
      Dimension prefSize = new Dimension(10, 10);
      Container parent = null;
      boolean sizeKnown = false;
      boolean positionsKnown = false;
      int lastY = TOP_OFFSET;


      /**
       *  Returns the alignment along the x axis.
       *
       * @param  target  Description of Parameter
       * @return         The layoutAlignmentX value
       * @since          2.9.12
       */
      public float getLayoutAlignmentX(Container target) {
         return 0.0f;
      }


      /**
       *  Returns the alignment along the y axis.
       *
       * @param  target  Description of Parameter
       * @return         The layoutAlignmentY value
       * @since          2.9.12
       */
      public float getLayoutAlignmentY(Container target) {
         computeSizeOfChildren(parent);
         Point pt = target.getLocation();
         return ((float)pt.y) / ((float)prefSize.height);
      }


      /**
       *  If the layout manager uses a per-component string, adds the component comp to the layout, associating it with
       *  the string specified by name.
       *
       * @param  name  The feature to be added to the layoutComponent attribute
       * @param  comp  The feature to be added to the layoutComponent attribute
       * @since        2.9.12
       */
      public void addLayoutComponent(String name, Component comp) {
         sizeKnown = false;
      }


      /**
       *  Lays out the specified container.
       *
       * @param  parent  Description of Parameter
       * @since          2.9.12
       */
      public void layoutContainer(Container parent) {
         this.parent = parent;
         UMLPackage pack = (UMLPackage)parent.getParent();
         double scale = pack.getScale();
         computePositions(parent);
         Insets insets = parent.getInsets();
         for (int i = 0; i < parent.getComponentCount(); i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible()) {
               Dimension d = c.getPreferredSize();
               Point pt = c.getLocation();
               // Set the component's size and position.
               int xinc = 0;
               if (c instanceof JLabel) {
                  xinc = (int)(((JLabel)c).getText().length() * scale);
               }
               if (c instanceof RoleHolder) {
                  //xinc = (int)(((RoleHolder)c).maxRoleWidth() * scale);
               }
               c.setBounds(X_OFFSET, pt.y, d.width + xinc, d.height);
            }
         }
      }


      /**
       *  Calculates the minimum size dimensions for the specified container, given the components it contains.
       *
       * @param  parent  Description of Parameter
       * @return         Description of the Returned Value
       * @since          2.9.12
       */
      public Dimension minimumLayoutSize(Container parent) {
         computeSize(parent);
         return minSize;
      }


      /**
       *  Calculates the preferred size dimensions for the specified container, given the components it contains.
       *
       * @param  parent  Description of Parameter
       * @return         Description of the Returned Value
       * @since          2.9.12
       */
      public Dimension preferredLayoutSize(Container parent) {
         computeSize(parent);
         return prefSize;
      }


      /**
       *  Removes the specified component from the layout.
       *
       * @param  comp  Description of Parameter
       * @since        2.9.12
       */
      public void removeLayoutComponent(Component comp) {
         sizeKnown = false;
      }


      /**
       *  Adds the specified component to the layout, using the specified constraint object.
       *
       * @param  comp         The feature to be added to the layoutComponent attribute
       * @param  constraints  The feature to be added to the layoutComponent attribute
       * @since               2.9.12
       */
      public void addLayoutComponent(Component comp, Object constraints) {
         lastY = initialisePosition(comp, lastY);
         sizeKnown = false;
      }


      /**
       *  Invalidates the layout, indicating that if the layout manager has cached information it should be discarded.
       *
       * @param  target  Description of Parameter
       * @since          2.9.12
       */
      public void invalidateLayout(Container target) {
         sizeKnown = false;
      }


      /**
       *  Calculates the maximum size dimensions for the specified container, given the components it contains.
       *
       * @param  target  Description of Parameter
       * @return         Description of the Returned Value
       * @since          2.9.12
       */
      public Dimension maximumLayoutSize(Container target) {
         computeSize(target);
         return prefSize;
      }


      /**
       *  Description of the Method
       *
       * @param  parent  Description of Parameter
       * @since          2.9.12
       */
      private void computeSizeOfChildren(Container parent) {
         if (sizeKnown) {
            return;
         }
         int maxX = 10;
         int maxY = 10;
         for (int i = 0; i < parent.getComponentCount(); i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible()) {
               Dimension d = c.getPreferredSize();
               int xinc = 0;
               if (c instanceof RoleHolder) {
                  Insets insets2 = ((RoleHolder)c).getInsets();
                  xinc = insets2.left + insets2.right;
               }
               Point pt = c.getLocation();
               if (maxX < d.width + xinc) {
                  maxX = d.width + xinc;
               }
               if (maxY < pt.y + d.height) {
                  maxY = pt.y + d.height;
               }
            }
         }
         minSize = new Dimension(maxX + X_OFFSET * 2, maxY + BOTTOM_OFFSET);
         prefSize = new Dimension(maxX + X_OFFSET * 2, maxY + BOTTOM_OFFSET);
      }


      /**
       *  Description of the Method
       *
       * @param  parent  Description of Parameter
       * @since          2.9.12
       */
      private void computeSize(Container parent) {
         if (sizeKnown) {
            return;
         }
         computeSizeOfChildren(parent);
         Insets insets = parent.getInsets();
         int extraWidth = insets.left + insets.right;
         int maxWidth = 0;
         Component[] components = parent.getComponents();
         for (int i = 0; i < components.length; i++) {
            Dimension d = components[i].getSize();
            int xinc = 0;
            if (components[i] instanceof RoleHolder) {
               Insets insets2 = ((RoleHolder)components[i]).getInsets();
               extraWidth += insets2.left + insets2.right;
            }
            if (maxWidth < d.width) {
               maxWidth = d.width;
            }
         }

         minSize.width = minSize.width + extraWidth;
         minSize.height = minSize.height + insets.top + insets.bottom;

         sizeKnown = true;
      }


      /**
       *  Description of the Method
       *
       * @param  parent  Description of Parameter
       * @since          2.9.12
       */
      private void computePositions(Container parent) {
         if (positionsKnown) {
            return;
         }
         lastY = TOP_OFFSET;
         Component[] components = parent.getComponents();
         for (int i = 0; i < components.length; i++) {
            lastY = initialisePosition(components[i], lastY);
         }
         positionsKnown = true;
      }


      /**
       *  Description of the Method
       *
       * @param  comp   Description of Parameter
       * @param  lastY  Description of Parameter
       * @return        Description of the Returned Value
       * @since         2.9.12
       */
      private int initialisePosition(Component comp, int lastY) {
         Dimension d = comp.getPreferredSize();
         comp.setLocation(X_OFFSET, lastY);
         return lastY + d.height;
      }

   }

}
