package edu.rosehulman.brahma;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.rosehulman.brahma.events.EventHandler;
import edu.rosehulman.brahma.events.Listener;
import edu.rosehulman.brahma.events.plugin.PluginDisableEvent;
import edu.rosehulman.brahma.events.plugin.PluginEnableEvent;
import edu.rosehulman.brahma.events.plugin.PluginLoadEvent;
import edu.rosehulman.brahma.events.plugin.PluginUnloadEvent;
import edu.rosehulman.brahma.plugin.Plugin;

public class BrahmaView implements Listener {
		// GUI Widgets that we will need
		private JFrame frame;
		private JPanel contentPane;
		private JLabel bottomLabel;
		private JPanel topBar;
		private JList sideList;
		private DefaultListModel<String> listModel;
		private JPanel centerEnvelope;
		private JPanel pluginContent;
		
		// For holding registered plugin
		private Plugin currentPlugin;
		
		// Holds a content pane for each plugin for resuming
		private Map<Plugin, JPanel> contentMap = new HashMap<Plugin, JPanel>();
		
		// Plugin manager
		IPluginManager pluginManager;
		
		public BrahmaView() {
			try {
				this.pluginManager = new PluginManager();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			this.pluginManager.registerEvents(this);
			
			// Lets create the elements that we will need
			frame = new JFrame("Pluggable Board Application");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			contentPane = (JPanel)frame.getContentPane();
			contentPane.setPreferredSize(new Dimension(700, 500));
			bottomLabel = new JLabel("No plugins registered yet!");
			
			listModel = new DefaultListModel<String>();
			sideList = new JList(listModel);
			sideList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			sideList.setLayoutOrientation(JList.VERTICAL);
			JScrollPane scrollPane = new JScrollPane(sideList);
			scrollPane.setPreferredSize(new Dimension(100, 50));
			
			// Create center display area
			centerEnvelope = new JPanel(new BorderLayout());
			centerEnvelope.setBorder(BorderFactory.createLineBorder(Color.black, 5));
			
			topBar = new JPanel();
			topBar.setPreferredSize(new Dimension(500, 50));
			JButton enableButton = new JButton("Enable Plugin");
			JButton disableButton = new JButton("Disable Plugin");
			
			enableButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					BrahmaView.this.pluginManager.enablePlugin(currentPlugin);
				}
			});
			
			disableButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					BrahmaView.this.pluginManager.disablePlugin(currentPlugin);
				}
			});
			
			topBar.add(enableButton);
			topBar.add(disableButton);
			
			// Lets lay them out, contentPane by default has BorderLayout as its layout manager
			contentPane.add(centerEnvelope, BorderLayout.CENTER);
			contentPane.add(scrollPane, BorderLayout.EAST);
			contentPane.add(bottomLabel, BorderLayout.SOUTH);
			contentPane.add(topBar, BorderLayout.NORTH);
			
			// Add action listeners
			sideList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					// If the list is still updating, return
					if(e.getValueIsAdjusting())
						return;
					
					// List has finalized selection, let's process further
					int index = sideList.getSelectedIndex();
					String id = listModel.elementAt(index);
					Plugin plugin = pluginManager.getPluginMap().get(id);
					
					if(plugin == null || plugin.equals(currentPlugin))
						return;
					
					// Store away current panel
					if (currentPlugin != null) {
						contentMap.put(currentPlugin, pluginContent);
					}
										
					// The newly selected plugin is our current plugin
					currentPlugin = plugin;
					
					centerEnvelope.removeAll();
					
					// Attempt to retrieve existing panel
					pluginContent = contentMap.get(currentPlugin);
					if (pluginContent == null) {
						pluginContent = new JPanel();
						// Ask plugin to layout the working area
						currentPlugin.layout(pluginContent);
					}
					centerEnvelope.add(pluginContent, BorderLayout.CENTER); 
					
					contentPane.revalidate();
					contentPane.repaint();
				}
			});
			
			// Start the plugin manager now that the core is ready
			Thread thread = new Thread(this.pluginManager);
			thread.start();
		}
		
		public void start() {
			EventQueue.invokeLater(new Runnable() {
				public void run()
				{
					frame.pack();
					frame.setVisible(true);
				}
			});
		}
		
		public void stop() {
			EventQueue.invokeLater(new Runnable() {
				public void run()
				{
					frame.setVisible(false);
				}
			});
		}
		
		@EventHandler
		public void addPluginToList(PluginLoadEvent event) {
			listModel.addElement(event.getPlugin().getName());
			this.bottomLabel.setText("The " + event.getPlugin().getName() + " plugin has been recently added!");
		}
		
		@EventHandler
		public void removePluginFromList(PluginUnloadEvent event) {
			listModel.removeElement(event.getPlugin().getName());
			this.bottomLabel.setText("The " + event.getPlugin().getName() + " plugin has been recently removed!");
		}
		
		@EventHandler
		public void pluginEnabled(PluginEnableEvent event) {
			bottomLabel.setText("The " + event.getPlugin().getName() + " is running!");
		}
		
		@EventHandler
		public void pluginDisabled(PluginDisableEvent event) {
			bottomLabel.setText("The " + event.getPlugin().getName() + " has stopped running!");
		}
}
