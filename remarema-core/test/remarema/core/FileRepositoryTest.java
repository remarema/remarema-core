package remarema.core;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.*;

public class FileRepositoryTest {

	private static final String RESOURCES_DIR = "./test/resources";
	private File repositoryRootDirectory;
	private FileRepository repository;

	@Before
	public void setup() {
		repositoryRootDirectory = new File(RESOURCES_DIR);
		Assert.assertTrue(repositoryRootDirectory.exists());
		repository = new FileRepository(repositoryRootDirectory);
	}

	@Test
	public void repository_root_is_repositoryRootDirectory() {
		assertThat(repository.getRootDirectory(), is(repositoryRootDirectory));
	}

	@Test
	public void get_subdirectory() {
		File file = repository.getSubdirectory("subdirectory");
		assertTrue(file.isDirectory());
	}

}
