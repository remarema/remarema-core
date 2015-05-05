package remarema.core;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.*;
import java.util.*;

import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Die Klasse testet.
 * 
 * @author Regina
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ClientTest {

	private Client client;

	@Mock
	private OutputStream outputStream;

	@Mock
	private FileRepository repository;

	@Mock
	private Server server;

	public FileInfo createFileInfoForFile(String name) {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setDirectory(false);
		fileInfo.setName(name);
		return fileInfo;
	}

	@Test
	public void do_not_download_if_file_is_up_to_date() {
		client = new Client(repository) {
			@Override
			public boolean isFileUpToDate(FileInfo fileInfo) {
				return true;
			}
		};

		setupClientAndServerForDownloadTest();

		client.synchronize(server);

		Mockito.verify(server).listFiles(".");
		Mockito.verifyNoMoreInteractions(server);
	}

	@Test
	public void download_if_file_is_not_up_to_date() {
		client = new Client(repository) {
			@Override
			public OutputStream createOutputStream(String path) {
				return outputStream;
			}

			@Override
			public boolean isFileUpToDate(FileInfo fileInfo) {
				return false;
			}
		};

		setupClientAndServerForDownloadTest();

		client.synchronize(server);

		Mockito.verify(server).listFiles(".");
		Mockito.verify(server).retrieveFile("one", outputStream);
		Mockito.verifyNoMoreInteractions(server);
	}

	@Test
	public void list_files_must_return_repository_contents() {
		FileInfo fileInfo = createFileInfoForFile("one");
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		fileList.add(fileInfo);
		when(repository.listFiles(".")).thenReturn(fileList);

		List<FileInfo> list = client.listFiles(".");
		assertFalse(list.isEmpty());
		assertThat(list.get(0).getName(), CoreMatchers.is("one"));
	}

	@Test
	public void navigate_to_subdirectories() {
		setupClientAndServerForSubdirectoryTest();

		client.synchronize(server);

		Mockito.verify(server).listFiles(".");
		Mockito.verify(server).listFiles("one");
		Mockito.verifyNoMoreInteractions(server);
	}

	@Test
	public void remove_obsolete_files() {
		client = new Client(repository) {
			@Override
			public void remove(String path) {
				assertThat(path, is("one"));
			}
		};

		setupClientAndServerRemoveFileTest();

		client.synchronize(server);

		Mockito.verify(server).listFiles(".");
		Mockito.verifyNoMoreInteractions(server);
	}

	@Before
	public void setup() {
		client = new Client(repository) {
			@Override
			public OutputStream createOutputStream(String path) {
				return outputStream;
			}
		};
	}

	public void setupClientAndServerForDownloadTest() {
		FileInfo fileInfo = createFileInfoForFile("one");
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		fileList.add(fileInfo);
		when(server.listFiles(".")).thenReturn(fileList);
		when(repository.listFiles(".")).thenReturn(new ArrayList<FileInfo>());
	}

	public void setupClientAndServerForSubdirectoryTest() {
		FileInfo fileInfo = createFileInfoForFile("one");
		fileInfo.setDirectory(true);
		List<FileInfo> rootDirectory = new ArrayList<FileInfo>();
		rootDirectory.add(fileInfo);
		when(server.listFiles(".")).thenReturn(rootDirectory);
		when(server.listFiles("one")).thenReturn(new ArrayList<FileInfo>());
		when(repository.listFiles(".")).thenReturn(new ArrayList<FileInfo>());
		when(repository.listFiles("one")).thenReturn(new ArrayList<FileInfo>());
	}

	private void setupClientAndServerRemoveFileTest() {
		FileInfo fileInfo = createFileInfoForFile("one");
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		fileList.add(fileInfo);
		when(repository.listFiles(".")).thenReturn(fileList);
		when(server.listFiles(".")).thenReturn(new ArrayList<FileInfo>());
	}

}
