package ru.sbrf.oir.toolbox.toolbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import ru.sbrf.oir.toolbox.toolbox.ScpFrom.MyUserInfo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshHelper {
	JSch jsch = null;
	static Session session = null;

	public SshHelper(String username, String hostname) {
		if (jsch == null) {
			jsch = new JSch();
		}
		if (session == null) {
			try {
				session = jsch.getSession(username, hostname);
				session.setUserInfo(new MyUserInfo());
			} catch (JSchException e) {
				System.err
						.println("Error!!! getSessint faild. SshHelper init fail.");
				e.printStackTrace();
			}
		}
	}

	public ArrayList<String> getLogFileList(String logPath, String perDays)
			throws Exception {
		ArrayList<String> findResult = new ArrayList<String>();
		// JSch jsch = new JSch();
		// Session session = jsch.getSession(username, hostname);
		session.connect();
		Channel channel = session.openChannel("exec");
		String execCommand = "find " + logPath + " -name \"*.log\" -atime -"
				+ perDays;
		System.out.println(execCommand);
		((ChannelExec) channel).setCommand(execCommand);

		InputStream in = channel.getInputStream();
		OutputStream out = channel.getOutputStream();
		((ChannelExec) channel).setErrStream(System.err);

		channel.connect();

		out.write(("\n").getBytes());
		out.flush();

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(in));
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			System.out.println(line);
			findResult.add(line);
		}
		System.out.println("exit status---: " + channel.getExitStatus());
		channel.disconnect();
//		session.disconnect();

		return findResult;
	}

	public void copyFile(ArrayList<String> srcFileList, String dstDirPath) {

		File dstDir = new File(dstDirPath);
		if(!dstDir.exists()){
			dstDir.mkdirs();
		}
		for (String srcFile : srcFileList) {

			FileOutputStream fos = null;
			try {

				String prefix = null;
				if (new File(dstDirPath).isDirectory()) {
					prefix = dstDirPath + File.separator;
				}

				if(!session.isConnected()) session.connect();

				// exec 'scp -f rfile' remotely
				String command = "scp -f " + srcFile;
				Channel channel = session.openChannel("exec");
				((ChannelExec) channel).setCommand(command);

				// get I/O streams for remote scp
				OutputStream out = channel.getOutputStream();
				InputStream in = channel.getInputStream();

				channel.connect();

				byte[] buf = new byte[1024];

				// send '\0'
				sendZero(out, buf);

				while (true) {
					int c = checkAck(in);
					if (c != 'C') {
						break;
					}

					// read '0644 '
					in.read(buf, 0, 5);

					long filesize = 0L;
					while (true) {
						if (in.read(buf, 0, 1) < 0) {
							// error
							break;
						}
						if (buf[0] == ' ')
							break;
						filesize = filesize * 10L + (long) (buf[0] - '0');
					}

					String file = null;
					for (int i = 0;; i++) {
						in.read(buf, i, 1);
						if (buf[i] == (byte) 0x0a) {
							file = new String(buf, 0, i);
							break;
						}
					}

					// System.out.println("filesize="+filesize+", file="+file);

					sendZero(out, buf);

					// read a content of lfile
					fos = new FileOutputStream(prefix == null ? dstDirPath : prefix
							+ file);
					int foo;
					while (true) {
						if (buf.length < filesize)
							foo = buf.length;
						else
							foo = (int) filesize;
						foo = in.read(buf, 0, foo);
						if (foo < 0) {
							// error
							break;
						}
						fos.write(buf, 0, foo);
						filesize -= foo;
						if (filesize == 0L)
							break;
					}
					fos.close();
					fos = null;

					if (checkAck(in) != 0) {
						System.exit(0);
					}

					sendZero(out, buf);
				}
				System.out.println("File "+ srcFile +" coped to" +dstDirPath);

//				session.disconnect();

//				System.exit(0);
			} catch (Exception e) {
				System.out.println(e);
				try {
					if (fos != null)
						fos.close();
				} catch (Exception ee) {
				}
			}
		}
	}

	private static void sendZero(OutputStream out, byte[] buf)
			throws IOException {
		buf[0] = 0;
		out.write(buf, 0, 1);
		out.flush();
	}

	static int checkAck(InputStream in) throws IOException {
		int b = in.read();
		// b may be 0 for success,
		// 1 for error,
		// 2 for fatal error,
		// -1
		if (b == 0)
			return b;
		if (b == -1)
			return b;

		if (b == 1 || b == 2) {
			StringBuffer sb = new StringBuffer();
			int c;
			do {
				c = in.read();
				sb.append((char) c);
			} while (c != '\n');
			if (b == 1) { // error
				System.out.print(sb.toString());
			}
			if (b == 2) { // fatal error
				System.out.print(sb.toString());
			}
		}
		return b;
	}
}