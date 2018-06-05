package me.coley.clicker.agent;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.security.CodeSource;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import me.coley.clicker.ui.MainGUI;
import net.bytebuddy.agent.ByteBuddyAgent;

/**
 * Entry point used for injecting the auto-clicker into another java process.
 * 
 * @author Matt
 */
public class Agent {
	public static void agentmain(String agentArgs, Instrumentation inst) {
		premain(agentArgs);
	}

	public static void premain(String agentArgs, Instrumentation inst) {
		premain(agentArgs);
	}

	public static void agentmain(String agentArgs) {
		premain(agentArgs);
	}

	public static void premain(String agentArgs) {
		if (agentArgs.contains("dir:")) {
			String dir = agentArgs.substring(agentArgs.indexOf(":") + 1);
			MainGUI.main(new String[] { "dir:" + dir, "displayAgentTab:false" });
		} else {
			MainGUI.main(new String[] { "displayAgentTab:false" });
		}
	}
	/**
	 * Loads this agent into a given VM.
	 * 
	 * @param name
	 *            Target VM display name.
	 * @param options
	 *            Launch arguments for the agent.
	 */
	public static void loadAgentToTaretFromVMName(String name, String options) {
		for (VirtualMachineDescriptor vm : VirtualMachine.list()) {
			if (vm.displayName().contains(name)) {
				loadAgentToTarget(vm.id(), options);
				break;
			}
		}
	}


	/**
	 * Loads this agent into a given VM.
	 * 
	 * @param pid
	 *            Process PID of target VM.
	 * @param options
	 *            Launch arguments for the agent.
	 */
	public static void loadAgentToTarget(String pid, String options) {
		try {
			ByteBuddyAgent.attach(getSelf(), pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the current path of the program file (jar).
	 * 
	 * @return
	 */
	private static File getSelf() throws Exception {
		CodeSource codeSource = Agent.class.getProtectionDomain().getCodeSource();
		File jarFile = new File(codeSource.getLocation().toURI().getPath());
		if (!jarFile.getName().toLowerCase().endsWith(".jar")) {
			throw new RuntimeException(
					"Please run recaf as a jar file to attach. You ran from: " + jarFile.getAbsolutePath());
		}
		return jarFile;
	}

	public static ListModel<String> getJVMS() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (VirtualMachineDescriptor vm : VirtualMachine.list()) {
			model.addElement(vm.displayName());
		}
		return model;
	}
}
