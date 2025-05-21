package com.example.myapplication.utils; // Adjust package if necessary

import android.content.Context;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileUtilsTest {

    @Mock
    Context mockContext;

    @Mock
    File mockFile;
    
    private static final String TEST_FILENAME = "test_file.txt";
    private static final String TEST_CONTENT = "This is a test string.\nWith multiple lines.";
    private static final String TEST_EMPTY_CONTENT = "";

    @Before
    public void setUp() throws Exception {
        // Mock context.getFilesDir() to return a temporary directory for testing
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        when(mockContext.getFilesDir()).thenReturn(tempDir);

        // Mock openFileOutput and openFileInput
        // These mocks are simplified and might need to be more robust for complex scenarios
        when(mockContext.openFileOutput(anyString(), anyInt()))
            .thenAnswer(invocation -> new FileOutputStream(new File(tempDir, (String) invocation.getArgument(0))));
        
        when(mockContext.openFileInput(anyString()))
            .thenAnswer(invocation -> new FileInputStream(new File(tempDir, (String) invocation.getArgument(0))));
        
        // Clean up any existing test file before each test
        File testFile = new File(tempDir, TEST_FILENAME);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void writeStringToFile_and_readStringFromFile_validContent() {
        // Write content
        assertTrue(FileUtils.writeStringToFile(mockContext, TEST_FILENAME, TEST_CONTENT));

        // Read content
        String readContent = FileUtils.readStringFromFile(mockContext, TEST_FILENAME);
        assertEquals(TEST_CONTENT.replaceAll("\\n", System.lineSeparator()), readContent.replaceAll("\\n", System.lineSeparator()));
    }

    @Test
    public void writeStringToFile_and_readStringFromFile_emptyContent() {
        // Write empty content
        assertTrue(FileUtils.writeStringToFile(mockContext, TEST_FILENAME, TEST_EMPTY_CONTENT));

        // Read content
        String readContent = FileUtils.readStringFromFile(mockContext, TEST_FILENAME);
        // Reading an empty file might result in null or empty string depending on implementation details of readLine.
        // The current readStringFromFile appends "\n" even for the last line if it's not null.
        // If the file is truly empty, readLine returns null immediately, loop isn't entered, and empty string is returned.
        // However, if an empty string was written, it means the file exists but has 0 bytes.
        // The current readStringFromFile might return a single "\n" if an empty string was written,
        // or "" if the file was empty to begin with. Let's adjust based on observed behavior or refine FileUtils.
        // For now, assuming writing "" results in a file that, when read, gives an empty string or just a newline.
        // The current FileUtils.readStringFromFile will append a newline if readLine returns an empty string once.
        // Let's assume writing "" and reading it back should result in "" or a single newline.
        // Given the loop `while ((line = bufferedReader.readLine()) != null)`, if the file is empty, it returns ""
        // If the file contains just a newline, it returns "

" due to the appended "\n".
        // If the file contains "", it returns ""
        assertEquals(TEST_EMPTY_CONTENT, readContent.replaceAll("\\n", System.lineSeparator()));
    }

    @Test
    public void readStringFromFile_nonExistentFile() {
        // Make sure the file doesn't exist first
        FileUtils.deleteInternalFile(mockContext, "non_existent_file.txt"); 
        String content = FileUtils.readStringFromFile(mockContext, "non_existent_file.txt");
        assertNull(content);
    }

    @Test
    public void deleteInternalFile_existingFile_deletesSuccessfully() {
        // First, create a file to delete
        FileUtils.writeStringToFile(mockContext, TEST_FILENAME, "content to delete");
        
        File fileToCheck = new File(mockContext.getFilesDir(), TEST_FILENAME);
        assertTrue("File should exist before delete", fileToCheck.exists());

        assertTrue(FileUtils.deleteInternalFile(mockContext, TEST_FILENAME));
        
        assertFalse("File should not exist after delete", fileToCheck.exists());
    }

    @Test
    public void deleteInternalFile_nonExistingFile_returnsTrue() {
        // Ensure file does not exist
        File nonExistingFile = new File(mockContext.getFilesDir(), "ghost_file.txt");
        if(nonExistingFile.exists()) nonExistingFile.delete();

        assertTrue(FileUtils.deleteInternalFile(mockContext, "ghost_file.txt"));
    }
}
