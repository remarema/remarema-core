package remarema.core;

import java.io.*;
import java.util.List;

/**
 * 
 * @author Regina
 *
 */
public class Client {

	private FileRepository repository;

	public Client(FileRepository repository) {
		this.repository = repository;
	}
/**
 * Es wird
 * @param directory
 * @return
 */
	public List<FileInfo> listFiles(String directory) {
		return repository.listFiles(directory);
	}

	public void remove(String path) {
		removeFile(repository.getFile(path));
	}

	private void removeFile(File file) {
		if (file.isDirectory()) {
			removeDirectory(file);
		} else {
			file.delete();
		}
	}

	private void removeDirectory(File directory) {
		File[] directoryContents = directory.listFiles();
		for (File file : directoryContents) {
			removeFile(file);
		}
		directory.delete();
	}

	public OutputStream createOutputStream(String path) {
		File file = repository.makeFileFromPath(path);
		if (file.exists()) {
			String msg = "can't create output stream for file:" + path;
			throw new RuntimeException(msg);
		}
		try {
			makeParentDirectory(file);
			return new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			String msg = "can't create output stream for file:" + path;
			throw new RuntimeException(msg);
		}
	}

	private void makeParentDirectory(File file) {
		File parent = file.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
	}

	public boolean isFileUpToDate(FileInfo fileInfo) {
		File file = repository.makeFileFromPath(fileInfo.getName());
		if (file.exists()) {
			return true;
		}
		return false;
	}

	public void synchronize(Server server) {
		synchronizeDirectory(server, ".");
	}

	public void synchronizeDirectory(Server server, String directory) {
		List<FileInfo> serverFiles = server.listFiles(directory);
		List<FileInfo> clientFiles = listFiles(directory);

		removeObsoleteFiles(serverFiles, clientFiles);

		for (FileInfo serverFile : serverFiles) {
			if (serverFile.isDirectory()) {
				synchronizeDirectory(server, serverFile.getName());
			} else {
				synchronizeFile(server, serverFile);
			}
		}

	}

	private void synchronizeFile(Server server, FileInfo fileInfo) {
		if (isFileUpToDate(fileInfo)) {
			return;
		}
		String fileName = fileInfo.getName();
		OutputStream outputStream = createOutputStream(fileName);
		try {
			server.retrieveFile(fileName, outputStream);
		} finally {
			close(outputStream);
		}
	}

	private void close(OutputStream outputStream) {
		try {
			outputStream.close();
		} catch (IOException e) {
			String msg = "OutputStream konnte nicht geschlossen werden";
			throw new RuntimeException(msg, e);
		}
	}

	private void removeObsoleteFiles(List<FileInfo> serverFiles,
			List<FileInfo> clientFiles) {
		for (FileInfo clientFile : clientFiles) {
			if (!clientFile.isInList(serverFiles)) {
				remove(clientFile.getName());
			}
		}
	}

}
