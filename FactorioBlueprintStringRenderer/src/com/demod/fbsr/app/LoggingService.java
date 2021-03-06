package com.demod.fbsr.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.JOptionPane;

import org.json.JSONObject;

import com.demod.factorio.Config;
import com.google.common.util.concurrent.AbstractIdleService;

public class LoggingService extends AbstractIdleService {

	static {
		PrintStream err = System.err;
		try {
			JSONObject configJson = Config.get().getJSONObject("logging");

			File file = new File(configJson.getString("file"));
			PrintStream fout = new PrintStream(new FileOutputStream(file), true);
			System.setOut(fout);
			System.setErr(fout);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace(err);
			throw new InternalError(e);
		}
	}

	@Override
	protected void shutDown() throws Exception {
		ServiceFinder.removeService(this);
	}

	@Override
	protected void startUp() throws Exception {
		ServiceFinder.addService(this);

	}

}
