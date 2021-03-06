/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demo;

import java.awt.Point;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.EventProcessingType;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.demo//Demo//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "DemoTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "org.demo.DemoTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_DemoAction",
        preferredID = "DemoTopComponent")
@Messages({
    "CTL_DemoAction=Demo",
    "CTL_DemoTopComponent=Demo Window",
    "HINT_DemoTopComponent=This is a Demo window"
})
public final class DemoTopComponent extends TopComponent {

    private Scene scene;
    
    public DemoTopComponent() {
        initComponents();
        setName(Bundle.CTL_DemoTopComponent());
        setToolTipText(Bundle.HINT_DemoTopComponent());
        
        scene = new Scene ();
        LayerWidget layer = new LayerWidget (scene);
        scene.addChild (layer);

        
        final LabelWidget source = new LabelWidget (scene, "Hold Ctrl key and drag this label");
        source.setPreferredLocation (new Point (100, 100));
        layer.addChild (source);

        LayerWidget interractionLayer = new LayerWidget (scene);
        scene.addChild (interractionLayer);
        
        WidgetAction selectAction = ActionFactory.createSelectAction(new SelectProvider() {
            @Override
            public boolean isAimingAllowed(Widget widget, Point localLocation, boolean invertSelection) {
                return true;
            }
            @Override
            public boolean isSelectionAllowed(Widget widget, Point localLocation, boolean invertSelection) {
                return true;
            }
            @Override
            public void select(Widget widget, Point localLocation, boolean invertSelection) {
                scene.setFocusedWidget(widget);
            }
        });
        
        WidgetAction connectAction = ActionFactory.createExtendedConnectAction (interractionLayer, new ConnectProvider() {
            public boolean isSourceWidget (Widget sourceWidget) {
                return sourceWidget == source;
            }
            public ConnectorState isTargetWidget (Widget sourceWidget, Widget targetWidget) {
                return ConnectorState.REJECT_AND_STOP;
            }
            public boolean hasCustomTargetWidgetResolver (Scene scene) {
                return false;
            }
            public Widget resolveTargetWidget (Scene scene, Point sceneLocation) {
                return null;
            }
            public void createConnection (Widget sourceWidget, Widget targetWidget) {
                Object o = sourceWidget;
            }
        });
        
        scene.setKeyEventProcessingType(EventProcessingType.FOCUSED_WIDGET_AND_ITS_CHILDREN);
        
        scene.getActions().addAction(connectAction);
        scene.getActions().addAction(selectAction);
        
        // Commented out for debugging purposes
        // scene.getActions().addAction(new KeyboardMoveAction());
        
        pane.setViewportView(scene.createView());
    }

    @Override
    protected void componentActivated() {
        scene.getView().requestFocusInWindow();
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pane = new javax.swing.JScrollPane();

        setLayout(new java.awt.CardLayout());
        add(pane, "card2");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane pane;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
    
    private final class KeyboardMoveAction extends WidgetAction.Adapter {
        @Override
        public State keyPressed(Widget widget, WidgetAction.WidgetKeyEvent event) {
            return State.CONSUMED;
        }
    }
}
