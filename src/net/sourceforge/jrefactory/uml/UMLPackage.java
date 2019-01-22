package net.sourceforge.jrefactory.uml;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.io.*;
import java.util.*;
import javax.swing.*;
import net.sourceforge.jrefactory.uml.line.*;
import net.sourceforge.jrefactory.uml.print.*;
import org.acm.seguin.awt.ExceptionPrinter;
import org.acm.seguin.ide.common.ClassListPanel;
import org.acm.seguin.summary.*;



/**
 *  Description of the Class
 *
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: UMLPackage.java,v 1.3 2004/05/04 15:41:36 mikeatkinson Exp $
 */
public class UMLPackage extends LinedPanel {
   boolean hasChanged = false;
   private JScrollPane scrollPane;
   private SegmentedLine currentLine = null;
   private PackageSummary summary;
   private boolean loading = false;
   private boolean first = true;
   private UMLMouseAdapter mouseListener;
   private DragPanelAdapter dragPanelAdapter;
   private RenderingHints renderingHints;

   private ClassListPanel classListPanel = null;

   private boolean printing = false;
   private Map forces = new HashMap();
   /**
    *  These hints are for the best quality rendering, public so that they may be used by the printing function.
    *
    * @since    2.9.12
    */
   public static RenderingHints qualityHints = null;
   private static RenderingHints speedHints = null;
   private static RenderingHints mediumHints = null;


   /**
    *  Constructor for the UMLPackage object
    *
    * @param  reader  Load the package from this reader.
    * @since          2.9.12
    */
   public UMLPackage(Reader reader) {
      super(new PackageLayout());
      this.summary = summary;
      initialise(new PackageLoader(this, reader));
   }


   /**
    *  Constructor for the UMLPackage object
    *
    * @param  summary  Description of Parameter
    * @since           2.9.12
    */
   public UMLPackage(PackageSummary summary) {
      super(new PackageLayout());
      this.summary = summary;
      initialise(new PackageLoader(this, summary));
   }


   /**
    *  Constructor for the UMLPackage object
    *
    * @param  summary   Description of Parameter
    * @param  settings  Description of Parameter
    * @since            2.9.12
    */
   public UMLPackage(PackageSummary summary, UMLSettings settings) {
      super(new PackageLayout());
      this.summary = summary;
      initialise(new PackageLoader(this, summary));
   }


   /**
    *  Constructor for the UMLPackage object
    *
    * @param  summary   Description of Parameter
    * @param  settings  Description of Parameter
    * @param  loader    Description of Parameter
    * @since            2.9.12
    */
   public UMLPackage(PackageSummary summary, UMLSettings settings, PackageLoader loader) {
      super(new PackageLayout());
      this.summary = summary;
      initialise(loader);
   }


   /**
    *  Sets the class list panel
    *
    * @param  value  the new list
    * @since         2.9.12
    */
   public void setClassListPanel(ClassListPanel value) {
      classListPanel = value;
   }


   /**
    *  Sets the ScrollPane attribute of the UMLPackage object
    *
    * @param  value  The new ScrollPane value
    * @since         2.9.12
    */
   public void setScrollPane(JScrollPane value) {
      scrollPane = value;
      scrollPane.getVerticalScrollBar().setModel(new MyRangeModel());
      scrollPane.getHorizontalScrollBar().setModel(new MyRangeModel());
   }


   public JScrollPane getScrollPane() {
      return scrollPane;
   }


   public class MyRangeModel extends javax.swing.DefaultBoundedRangeModel {
      public MyRangeModel() {
         super();
      }
      public MyRangeModel(int value, int extent, int min, int max) {
         super(value, extent, min, max);
      }
      public int getMaximum() {
         return (int)(super.getMaximum()*scale);
      }
   }

   /**
    *  Sets the rendering quality attribute of the UMLPackage.
    *
    * @param  type  The new quality value
    * @since        2.9.12
    */
   public void setQuality(String type) {
      if ("fast".equals(type)) {
         renderingHints = speedHints;
      } else if ("quality".equals(type)) {
         renderingHints = qualityHints;
      } else {
         renderingHints = mediumHints;
      }
   }


   /**
    *  Sets the view attribute of the UMLPackage object
    *
    * @param  type      The new view value
    * @param  selected  The new view value
    * @since            2.9.12
    */
   public void setView(String type, boolean selected) {
      if ("private".equals(type)) {
         Component[] components = getComponents();
         for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof UMLType) {
               UMLType umlType = (UMLType)components[i];
               if (umlType.isViewPrivate() != selected) {
                  umlType.setViewPrivate(selected);
                  hasChanged = true;
               }
            }
         }
      } else if ("external".equals(type)) {
         Component[] components = getComponents();
         for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof UMLType) {
               UMLType umlType = (UMLType)components[i];
               if (umlType.isForeign()) {
                  if (umlType.isVisible() != selected) {
                     umlType.setVisible(selected);
                     hasChanged = true;
                  }
               }
            }
         }
      } else {

      }
   }


   /**
    *  Sets the printing attribute of the UMLPackage object
    *
    * @param  printing  The new printing value
    * @since            2.9.12
    */
   public void setPrinting(boolean printing) {
      this.printing = printing;
   }


   /**
    *  Sets the Dirty attribute of the UMLPackage object
    *
    * @since    2.9.12
    */
   public void setDirty() {
      hasChanged = true;
   }


   /**
    *  Sets the summary attribute of the UMLPackage object
    *
    * @param  summary  The new summary value
    * @since           2.9.12
    */
   public void setSummary(PackageSummary summary) {
      this.summary = summary;
   }


   /**
    *  Sets the loading value
    *
    * @param  value  The new Loading value
    * @since         2.9.12
    */
   public void setLoading(boolean value) {
      loading = value;
   }


   /**
    *  Gets the viewPrivate attribute of the UMLPackage object
    *
    * @return    The viewPrivate value
    * @since     2.9.12
    */
   public boolean isViewPrivate() {
      Component[] components = getComponents();
      for (int i = 0; i < components.length; i++) {
         if (components[i] instanceof UMLType) {
            UMLType umlType = (UMLType)components[i];
            return umlType.isViewPrivate();
         }
      }
      return true;
   }


   /**
    *  Gets the viewExternal attribute of the UMLPackage object
    *
    * @return    The viewExternal value
    * @since     2.9.12
    */
   public boolean isViewExternal() {
      Component[] components = getComponents();
      for (int i = 0; i < components.length; i++) {
         if (components[i] instanceof UMLType) {
            UMLType umlType = (UMLType)components[i];
            if (umlType.isForeign()) {
               return umlType.isVisible();
            }
         }
      }
      return true;
   }


   /**
    *  Returns the preferred size
    *
    * @return    The size
    * @since     2.9.12
    */
   public Dimension getPreferredSize() {
      //  Initialize local variables
      int wide = 10;
      int high = 10;
      Component[] children = getComponents();
      for (int ndx = 0; ndx < children.length; ndx++) {
         Rectangle bounds = children[ndx].getBounds();
         wide = Math.max(wide, 20 + bounds.x + bounds.width);
         high = Math.max(high, 20 + bounds.y + bounds.height);
      }

      return new Dimension(wide, high);
   }


   /**
    *  Gets the dirty attribute of the UMLPackage object
    *
    * @return    The dirty value
    * @since     2.9.12
    */
   public boolean isDirty() {
      return hasChanged;
   }


   /**
    *  Gets the summary attribute of the UMLPackage object
    *
    * @return    The summary value
    * @since     2.9.12
    */
   public PackageSummary getSummary() {
      return summary;
   }


   /**
    *  Get the components that are UMLTypes
    *
    * @return    Description of the Returned Value
    * @since     2.9.12
    */
   public UMLType[] getTypes() {
      Component[] children = getComponents();
      int last = children.length;
      int count = 0;

      //  Count the UMLTypes
      for (int ndx = 0; ndx < last; ndx++) {
         if (children[ndx] instanceof UMLType) {
            count++;
         }
      }

      //  Count the UMLTypes
      UMLType[] results = new UMLType[count];
      int item = 0;
      for (int ndx = 0; ndx < last; ndx++) {
         if (children[ndx] instanceof UMLType) {
            results[item] = (UMLType)children[ndx];
            item++;
         }
      }

      //  Return the result
      return results;
   }


   /**
    *  Description of the Method
    *
    * @since    2.9.12
    */
   public void updateListeners() {
      addMouseListener(mouseListener);
      addMouseListener(dragPanelAdapter);
      addMouseMotionListener(dragPanelAdapter);
      Component[] components = getComponents();
      for (int i = 0; i < components.length; i++) {
         if (components[i] instanceof UMLType) {
            UMLType type = (UMLType)components[i];
            MouseListener[] mls = type.getMouseListeners();
            for (int j = 0; j < mls.length; j++) {
               type.removeMouseListener(mls[j]);
            }
            MouseMotionListener[] mmls = type.getMouseMotionListeners();
            for (int j = 0; j < mmls.length; j++) {
               type.removeMouseMotionListener(mmls[j]);
            }
            type.addMouseListener(mouseListener);
            type.addMouseListener(dragPanelAdapter);
            type.addMouseMotionListener(dragPanelAdapter);
         }
      }
   }


   /**
    *  Description of the Method
    *
    * @exception  java.io.IOException  Description of Exception
    * @since                           2.9.12
    */
   public void save() throws java.io.IOException {
      //  Make sure we have something that has changed
      if (!hasChanged) {
         return;
      }

      //  Local Variables
      Component[] children = getComponents();
      int last = children.length;

      File outputFile = PackageLoader.getFile(summary);
      PrintWriter output = new PrintWriter(new FileWriter(outputFile));

      output.println("V[1.1:" + summary.getName() + "]");

      //  Save the line segments
      Iterator iter = getLineIterator();
      while (iter.hasNext()) {
         ((SegmentedLine)iter.next()).save(output);
      }

      //  Save the types
      for (int ndx = 0; ndx < last; ndx++) {
         if (children[ndx] instanceof UMLType) {
            ((UMLType)children[ndx]).save(output);
         }
      }

      output.close();

      //  Nothing has changed
      hasChanged = false;
   }


   /**
    *  Description of the Method
    *
    * @param  g  Description of Parameter
    * @since     2.9.12
    */
   public void paint(Graphics g) {
      if (!printing) {
         ((Graphics2D)g).setRenderingHints(renderingHints);
         Rectangle bounds = g.getClipBounds();
         if (bounds != null) {
            g.clearRect(bounds.x, bounds.y, bounds.width, bounds.height);
         }
      }
      ((Graphics2D)g).scale(scale, scale);

      Dimension size = getSize();
      //  Draw the grid
      PageFormat pf = UMLPagePrinter.getPageFormat(false);
      if (pf != null) {
         int pageHeight = (int)UMLPagePrinter.getPageHeight();
         int pageWidth = (int)UMLPagePrinter.getPageWidth();

         g.setColor(Color.gray);
         for (int x = pageWidth; x < size.width; x += pageWidth) {
            g.drawLine(x, 0, x, (int)(size.height / scale));
         }

         for (int y = pageHeight; y < size.width; y += pageHeight) {
            g.drawLine(0, y, (int)(size.width / scale), y);
         }
      }

      //  Draw the segmented lines
      Iterator iter = getLineIterator();
      while (iter.hasNext()) {
         ((SegmentedLine)iter.next()).paint(g);
      }

      paintChildren(g);
      ((Graphics2D)g).scale(1.0 / scale, 1.0 / scale);
   }


   /**
    *  Dragging a segmented line point
    *
    * @param  actual  The mouse's current location
    * @since          2.9.12
    */
   public void drag(Point actual) {
      Point virtual = new Point((int)(actual.x / scale), (int)(actual.y / scale));
      if (currentLine != null) {
         currentLine.drag(virtual);
         repaint();
      }
   }


   /**
    *  User dropped an item
    *
    * @since    2.9.12
    */
   public void drop() {
      if (currentLine != null) {
         currentLine.drop();
         hasChanged = true;
         currentLine = null;
      }
      reset();
   }


   /**
    *  Determine what you hit
    *
    * @param  actual  The hit location
    * @since          2.9.12
    */
   public void hit(Point actual) {
      Point virtual = new Point((int)(actual.x / scale), (int)(actual.y / scale));
      currentLine = null;
      Iterator iter = getLineIterator();
      while ((currentLine == null) && iter.hasNext()) {
         SegmentedLine next = (SegmentedLine)iter.next();
         if (next.hit(virtual)) {
            currentLine = next;
         }
      }
      while (iter.hasNext()) {
         SegmentedLine next = (SegmentedLine)iter.next();
         next.select(false);
      }
      repaint();
   }


   /**
    *  Resets the scroll panes
    *
    * @since    2.9.12
    */
   public void reset() {
      if (scrollPane == null) {
         setSize(getPreferredSize());
         repaint();
      } else {
         Dimension panelSize = getPreferredSize();
         JViewport view = scrollPane.getViewport();
         JScrollBar hsb = scrollPane.getHorizontalScrollBar();
         JScrollBar vsb = scrollPane.getVerticalScrollBar();
         setSize(Math.min(panelSize.width, hsb.getMaximum()),
                 Math.min(panelSize.height, vsb.getMaximum()));
         repaint();
      }
   }


   /**
    *  Description of the Method
    *
    * @since    2.9.12
    */
   public void clear() {
      removeAll();
      super.clear();
   }


   /**
    *  Reloads the UML class diagrams
    *
    * @since    2.9.12
    */
   public void reload() {
      //  Save the image
      try {
         save();
      } catch (IOException ioe) {
         ExceptionPrinter.print(ioe, false);
      }

      new Thread(new Runnable() {
         public void run() {
            (new PackageLoader(UMLPackage.this, UMLPackage.this.summary)).run();  //  Reload it
            setSize(getPreferredSize());  //  Reset the size
            reset();
            validate();
            hasChanged = false;  //  Nothing has changed yet
         }
      }).start();
   }
 


   /**
    *  Remove the association
    *
    * @param  field  Description of Parameter
    * @since         2.9.12
    */
   public void removeAssociation(UMLField field) {
      EndPointPanel end = null;
      Iterator iter = getLineIterator();
      while (iter.hasNext()) {
         Object next = iter.next();
         if (next instanceof AssociationRelationship) {
            AssociationRelationship assoc = (AssociationRelationship)next;
            if (assoc.getField().equals(field)) {
               assoc.delete();
               iter.remove();
               end = assoc.getEndPanel();
               break;
            }
         }
      }
      // check to see whether the end of the association is used elsewhere.
      boolean found = false;
      if (end != null) {
         iter = getLineIterator();
         while (iter.hasNext()) {
            Object next = iter.next();
            if (next instanceof SegmentedLine) {
               SegmentedLine line = (SegmentedLine)next;
               if (end == line.getStartPanel()) {
                  found = true;
                  break;
               } else if (end == line.getEndPanel()) {
                  found = true;
                  break;
               }
            }
         }
      }
      // If the end does not occur in any other association then, if it is a Type
      // and does not belong in this package we can remove it.
      if (!found) {
         if (end instanceof UMLType) {
            UMLType type = (UMLType)end;
            TypeSummary ts = (TypeSummary)type.getSourceSummary();
            PackageSummary ps = ts.getPackageSummary();
            if (ps != summary) {
               remove(end);
            }
         }
      }
   }


   /**
    *  Tells the scrollbar to jump to this location
    *
    * @param  type  Description of Parameter
    * @since        2.9.12
    */
   public void jumpTo(TypeSummary type) {
      UMLType umlType = findType(type);
      jumpTo(umlType);
   }


   /**
    *  Tells the scrollbar to jump to this location
    *
    * @param  type  Description of Parameter
    * @since        2.9.15
    */
   public void jumpTo(UMLType umlType) {
      if (umlType == null) {
         return;
      }

      Point pt = umlType.getLocation();
      java.awt.Dimension sz = umlType.getSize();

      JScrollBar horiz = scrollPane.getHorizontalScrollBar();
      JScrollBar vert = scrollPane.getVerticalScrollBar();
      if (    (horiz.getValue()+10) < pt.x / scale
           && (horiz.getValue()+horiz.getVisibleAmount()) > (pt.x+sz.width) / scale
           && (vert.getValue()+10) < pt.y / scale
           && (vert.getValue()+vert.getVisibleAmount()) > (pt.y+sz.height) / scale ) {
         return;
      }
      horiz.setValue(pt.x - 10);
      vert.setValue(pt.y - 10);
   }


   /**
    *  Initialises the positions of all types randomly.
    *
    * @since    2.7.04
    */
   public void resetPositions() {
      UMLType[] types = getTypes();
      int size = (int)(Math.sqrt(types.length) * 1000);
      Random r = new Random();
      for (int i = 0; i < types.length; i++) {
         types[i].setLocation(r.nextInt(size), r.nextInt(size));
      }
   }


   /**
    *  Uses a mixture of Simulated Annealling, springs along relationships and gravity (to position subclasses below
    *  their parents).
    *
    * @param  temperature     notional initial temperature of the diagram, a value of 2000 works well.
    * @param  iterations      number of times to reduce temperature
    * @param  springStrength  Description of Parameter
    * @since                  2.7.04
    */
   public void rearragePositions(int temperature, int iterations, double springStrength) {
      double oldScale = scale;
      scale(1.0);
      Map groups = group();
      Random r = new Random();
      UMLType[] types = getTypes();

      for (int times = 1; times <= iterations; times++) {
         for (int i = 0; i < types.length; i++) {
            types[i].shift(r.nextInt(temperature) / (times * times), r.nextInt(temperature) / (times * times * times));
         }
         forces = new HashMap();
         Iterator iter = getLineIterator();
         while (iter.hasNext()) {
            SegmentedLine line = (SegmentedLine)iter.next();
            EndPointPanel start = line.getStartPanel();
            EndPointPanel end = line.getEndPanel();
            if (times < (iterations - 10)) {
               addForce(start, end, start.getBounds(), end.getBounds(), -springStrength * 0.5 / times);
               if (line instanceof InheretenceRelationship || line instanceof ImplementsRelationship) {
                  addGravity(start, temperature / (times * times));
                  addGravity(end, -temperature / (times * times));
               }
            }
         }
         for (int i = 0; i < types.length; i++) {
            for (int j = i + 1; j < types.length; j++) {
               EndPointPanel start = types[i];
               EndPointPanel end = types[j];
               int startGroup = ((Integer)groups.get(start)).intValue();
               int endGroup = ((Integer)groups.get(end)).intValue();
               double pushStrength = (startGroup == endGroup) ? springStrength : springStrength * 4.0;
               double pullStrength = (startGroup == endGroup) ? springStrength * 0.3 : springStrength * 0.5 / times;

               Rectangle boundsStart = start.getBounds();
               Rectangle boundsEnd = end.getBounds();

               boundsStart.x -= 60;
               boundsStart.y -= 60;
               boundsStart.width += 120;
               boundsStart.height += 120;
               boundsEnd.x -= 60;
               boundsEnd.y -= 60;
               boundsEnd.width += 120;
               boundsEnd.height += 120;
               if (boundsStart.intersects(boundsEnd)) {
                  addInvSqForce(start, end, boundsStart, boundsEnd, pushStrength * 40.0);
               } else {
                  if (times < (iterations - 10)) {
                     addForce(start, end, start.getBounds(), end.getBounds(), -pullStrength / (times * types.length));
                  }
               }
            }
         }
         Iterator i = forces.keySet().iterator();
         while (i.hasNext()) {
            EndPointPanel t = (EndPointPanel)i.next();
            Pair pair = (Pair)forces.get(t);
            t.shift((int)pair.x, (int)pair.y);
         }
      }
      int minX = Integer.MAX_VALUE;
      int minY = Integer.MAX_VALUE;
      for (int i = 0; i < types.length; i++) {
         Rectangle bounds = types[i].getBounds();
         minX = Math.min(minX, bounds.x);
         minY = Math.min(minY, bounds.y);
      }
      for (int i = 0; i < types.length; i++) {
         types[i].shift(20 - minX, 20 - minY);
      }
      forces = null;
      scale(oldScale);
   }


   /**
    *  Find the type based on a id code
    *
    * @param  panel1  Description of Parameter
    * @param  panel2  Description of Parameter
    * @return         the UML type object
    * @since          2.9.12
    */
   protected SegmentedLine find(String panel1, String panel2) {
      UMLType first = find(panel1);
      UMLType second = find(panel2);

      if ((first == null) || (second == null)) {
         return null;
      }

      Iterator iter = getLineIterator();
      while (iter.hasNext()) {
         SegmentedLine line = (SegmentedLine)iter.next();
         if (line.match(first, second)) {
            return line;
         }
      }

      return null;
   }


   /**
    *  Find the type based on a id code
    *
    * @param  id  the code we are searching for
    * @return     the UML type object
    * @since      2.9.12
    */
   protected UMLType find(String id) {
      if (id == null) {
         return null;
      }

      Component[] children = getComponents();

      //  Find the id that matches
      for (int ndx = 0; ndx < children.length; ndx++) {
         if (children[ndx] instanceof UMLType) {
            String current = ((UMLType)children[ndx]).getID();
            if (id.equals(current)) {
               return (UMLType)children[ndx];
            }
         }
      }

      return null;  //  Not found
   }


   /**
    *  Find the type based on a summary
    *
    * @param  searching  the variable we are searching for
    * @return            the UML type object
    * @since             2.9.12
    */
   protected UMLType findType(TypeSummary searching) {
      if (searching == null) {
         return null;
      }

      Component[] children = getComponents();
      for (int ndx = 0; ndx < children.length; ndx++) {
         if (children[ndx] instanceof UMLType) {
            TypeSummary current = ((UMLType)children[ndx]).getSummary();
            if (searching.equals(current)) {
               return (UMLType)children[ndx];
            }
         }
      }
      return null;  //  Not found
   }


   /**
    *  Tells the class list panel to laod itself
    *
    * @since    2.9.12
    */
   void updateClassListPanel() {
      setSize(getPreferredSize());
      if (classListPanel == null) {
         return;
      }
      if (first) {
         first = false;
      } else {
         classListPanel.load(summary);
      }
   }


   /**
    *  Description of the Method
    *
    * @param  loader  Description of Parameter
    * @since          2.9.12
    */
   private void initialise(final PackageLoader loader) {
      UMLSettings.initData();
      mouseListener = new UMLMouseAdapter(this, null);
      dragPanelAdapter = new DragPanelAdapter(this);
      if (speedHints == null) {
         speedHints = new RenderingHints(new HashMap());
         speedHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
         speedHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
         speedHints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
         speedHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
         speedHints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
         //speedHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
         speedHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
         speedHints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
      }
      if (qualityHints == null) {
         qualityHints = new RenderingHints(new HashMap());
         qualityHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
         qualityHints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
         qualityHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         qualityHints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
         qualityHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
         qualityHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
         qualityHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
         qualityHints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
      }
      if (mediumHints == null) {
         mediumHints = new RenderingHints(new HashMap());
         mediumHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         mediumHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
         mediumHints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
         mediumHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         mediumHints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
         mediumHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
         mediumHints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
         mediumHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
         mediumHints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
      }
      renderingHints = speedHints;
      addMouseAdapter();
      new Thread(new Runnable() {
         public void run() {
            loader.run();  //  Reload it
            setSize(getPreferredSize());  //  Reset the size
            reset();
            validate();
            hasChanged = false;  //  Nothing has changed yet
         }
      }).start();
   }


   /**
    *  Adds a feature to the MouseAdapter attribute of the UMLPackage object
    *
    * @since    2.9.12
    */
   private void addMouseAdapter() {
      LineMouseAdapter adapter = new LineMouseAdapter(this);
      addMouseListener(adapter);
      addMouseMotionListener(adapter);
   }


   /**
    *  Adds a feature to the gravity attribute of the UMLPackage object
    *
    * @param  start     The feature to be added to the gravity attribute
    * @param  strength  The feature to be added to the gravity attribute
    * @since            2.9.12
    */
   private void addGravity(EndPointPanel start, double strength) {
      Pair pair = (Pair)forces.get(start);
      if (pair == null) {
         pair = new Pair();
         forces.put(start, pair);
      }
      pair.y += strength;
   }


   /**
    *  Adds a feature to the force attribute of the UMLPackage object
    *
    * @param  start        The feature to be added to the force attribute
    * @param  end          The feature to be added to the force attribute
    * @param  boundsStart  The feature to be added to the force attribute
    * @param  boundsEnd    The feature to be added to the force attribute
    * @param  strength     The feature to be added to the force attribute
    * @since               2.9.12
    */
   private void addForce(EndPointPanel start, EndPointPanel end, Rectangle boundsStart, Rectangle boundsEnd, double strength) {
      int startMiddleX = boundsStart.x + boundsStart.width / 2;
      int startMiddleY = boundsStart.y + boundsStart.height / 2;
      int endMiddleX = boundsEnd.x + boundsEnd.width / 2;
      int endMiddleY = boundsEnd.y + boundsEnd.height / 2;
      int diffX = startMiddleX - endMiddleX;
      int diffY = startMiddleY - endMiddleY;
      Pair pair = (Pair)forces.get(start);
      if (pair == null) {
         pair = new Pair();
         forces.put(start, pair);
      }

      pair.x += strength * diffX;
      pair.y += strength * diffY;
      pair = (Pair)forces.get(end);
      if (pair == null) {
         pair = new Pair();
         forces.put(end, pair);
      }
      pair.x -= strength * diffX;
      pair.y -= strength * diffY;
   }


   /**
    *  Adds a feature to the invSqForce attribute of the UMLPackage object
    *
    * @param  start        The feature to be added to the invSqForce attribute
    * @param  end          The feature to be added to the invSqForce attribute
    * @param  boundsStart  The feature to be added to the invSqForce attribute
    * @param  boundsEnd    The feature to be added to the invSqForce attribute
    * @param  strength     The feature to be added to the invSqForce attribute
    * @since               2.9.12
    */
   private void addInvSqForce(EndPointPanel start, EndPointPanel end, Rectangle boundsStart, Rectangle boundsEnd, double strength) {
      int startMiddleX = boundsStart.x + boundsStart.width / 2;
      int startMiddleY = boundsStart.y + boundsStart.height / 2;
      int endMiddleX = boundsEnd.x + boundsEnd.width / 2;
      int endMiddleY = boundsEnd.y + boundsEnd.height / 2;
      int diffX = startMiddleX - endMiddleX;
      int diffY = startMiddleY - endMiddleY;
      double dSquared = diffX * diffX + diffY * diffY;
      double d = Math.sqrt(dSquared);
      if (dSquared < 100) {
         dSquared = 100;
      }
      strength *= Math.sqrt(boundsStart.width * boundsStart.height);
      strength *= Math.sqrt(boundsEnd.width * boundsEnd.height);
      strength /= dSquared;
      Pair pair = (Pair)forces.get(start);
      if (pair == null) {
         pair = new Pair();
         forces.put(start, pair);
      }

      pair.x += strength * diffX / d;
      pair.y += strength * diffY / d;
      pair = (Pair)forces.get(end);
      if (pair == null) {
         pair = new Pair();
         forces.put(end, pair);
      }
      pair.x -= strength * diffX / d;
      pair.y -= strength * diffY / d;
   }


   /**
    *  Description of the Method
    *
    * @return    Description of the Returned Value
    * @since     2.9.12
    */
   private Map group() {
      UMLType[] types = getTypes();
      Map groups = new HashMap();
      for (int i = 0; i < types.length; i++) {
         groups.put(types[i], new Integer(i));
      }
      Iterator iter = getLineIterator();
      while (iter.hasNext()) {
         SegmentedLine line = (SegmentedLine)iter.next();
         EndPointPanel start = line.getStartPanel();
         EndPointPanel end = line.getEndPanel();
         int startGroup = ((Integer)groups.get(start)).intValue();
         int endGroup = ((Integer)groups.get(end)).intValue();
         if (startGroup != endGroup) {
            // join groups
            Iterator g = groups.entrySet().iterator();
            while (g.hasNext()) {
               Map.Entry entry = (Map.Entry)g.next();
               int v = ((Integer)entry.getValue()).intValue();
               if (v == endGroup) {
                  entry.setValue(new Integer(startGroup));
               }
            }
         }
      }
      return groups;
   }


   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    * @since     2.9.12
    */
   private static class PackageLayout implements LayoutManager2 {
      Dimension minSize = new Dimension(100, 100);
      Dimension prefSize = new Dimension(100, 100);
      Map components = new HashMap();
      boolean sizeKnown = false;
      boolean positionsKnown = false;
      Random random = new Random();


      /**
       *  Returns the alignment along the x axis.
       *
       * @param  target  Description of Parameter
       * @return         The layoutAlignmentX value
       * @since          2.9.12
       */
      public float getLayoutAlignmentX(Container target) {
         computeSize();
         Point pt = target.getLocation();
         return pt.x / (float)prefSize.width;
      }


      /**
       *  Returns the alignment along the y axis.
       *
       * @param  target  Description of Parameter
       * @return         The layoutAlignmentY value
       * @since          2.9.12
       */
      public float getLayoutAlignmentY(Container target) {
         computeSize();
         Point pt = target.getLocation();
         return pt.y / (float)prefSize.height;
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
         computePositions();
         Insets insets = parent.getInsets();
         int nComps = parent.getComponentCount();
         for (int i = 0; i < nComps; i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible()) {
               Dimension d = c.getPreferredSize();
               Point pt = c.getLocation();
               // Set the component's size and position.
               c.setBounds(pt.x, pt.y, d.width, d.height);
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
         components.remove(comp);
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
         components.put(comp, constraints);
         initialisePosition(comp);
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
       * @since    2.9.12
       */
      private void computeSize() {
         if (sizeKnown) {
            return;
         }
         int maxX = 100;
         int maxY = 100;
         for (Iterator i = components.keySet().iterator(); i.hasNext(); ) {
            Component c = (Component)i.next();
            if (c.isVisible()) {
               Dimension d = c.getPreferredSize();
               Point pt = c.getLocation();
               if (maxX < pt.x + d.width) {
                  maxX = pt.x + d.width;
               }
               if (maxY < pt.y + d.height) {
                  maxY = pt.y + d.height;
               }
            }
         }
         minSize = new Dimension(maxX, maxY);
         prefSize = new Dimension(maxX, maxY);
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
         computeSize();
         Insets insets = parent.getInsets();
         minSize.width = minSize.width + insets.left + insets.right;
         minSize.height = minSize.height + insets.top + insets.bottom;
         sizeKnown = true;
      }


      /**
       *  Description of the Method
       *
       * @since    2.9.12
       */
      private void computePositions() {
         if (positionsKnown) {
            return;
         }
         for (Iterator i = components.keySet().iterator(); i.hasNext(); ) {
            Component c = (Component)i.next();
            initialisePosition(c);
         }
         positionsKnown = true;
      }


      /**
       *  Description of the Method
       *
       * @param  comp  Description of Parameter
       * @since        2.9.12
       */
      private void initialisePosition(Component comp) {
         comp.setLocation(10 + random.nextInt(1000), 10 + random.nextInt(1000));
      }

   }


   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    * @since     2.9.12
    */
   private static class Pair {
      double x = 0.0;
      double y = 0.0;
   }
}
