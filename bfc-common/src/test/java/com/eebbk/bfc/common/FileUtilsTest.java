package com.eebbk.bfc.common;

import com.eebbk.bfc.common.file.FileUtils;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 2016/10/10.
 */

@Ignore
public class FileUtilsTest {
    public final static String createFilePath = "./abc/123/zy.text";
    @Test
    public void testCreateFile(){
        String createFilePath = "./abc/123/zy.text";
        boolean isCreateSuccess = FileUtils.createFileOrExists( createFilePath );
        Assert.assertEquals( true, isCreateSuccess  );
        Assert.assertEquals(true, FileUtils.isFile(createFilePath));
        Assert.assertEquals(false, FileUtils.isDir(createFilePath));
    }

    @Test
    public void testExist(){
        String createFilePath = "./abc/123/zy.text";
        boolean isCreateSuccess = FileUtils.createFileOrExists( createFilePath );
        if (!isCreateSuccess) {
            Assert.fail( "创建文件失败, 没必要往下走了 - -" );
        }

        Assert.assertEquals(true, FileUtils.isFileExists(createFilePath));
        Assert.assertEquals(true, FileUtils.isFileExists( new File(createFilePath)));

        String dirStr = new File(createFilePath).getParent();
        Assert.assertEquals(true, FileUtils.isFileExists(  dirStr  ));
        Assert.assertEquals(true, FileUtils.isFileExists(  new File(dirStr)  ));
    }

    @Test
    public void testSize(){

        File file = new File(createFilePath);

        boolean isCreateSuccess = FileUtils.createFileOrExists( file );
        if (!isCreateSuccess) {
            Assert.fail( "创建文件失败, 没必要往下走了 - -" );
        }

        System.out.println("文件大小: " + FileUtils.getFileSize(file));
        System.out.println("文件可用大小: " + FileUtils.getFileAvaiableSize( file ));
        System.out.println("文件可用大小: " +  FileUtils.byte2FitSize(FileUtils.getFileAvaiableSize( file )));
//        System.out.println("文件可用大小: " + FileUtils.getFileAvaiableSize(new File("")));
    }

    @Test
    public void testPath(){
//        File file = new File(createFilePath);
        File file = new File("./sdf/123/");

        System.out.println( file.getName() );

        System.out.println( FileUtils.getDirName( file ) );
        Assert.assertEquals("zy.text", FileUtils.getFileName( file ));
        Assert.assertEquals("text", FileUtils.getFileExtension( file ));
    }

    @Test
    public void testGetListFilesInDirWithFilter(){
        List<String> suffixList = new ArrayList<>();
        suffixList.add("txt");
        suffixList.add("docx");
        List<String> dirFilterList = new ArrayList<>();
        dirFilterList.add("d");
        dirFilterList.add("e");
        List<File> fileList = FileUtils.listFilesInDirWithFilter(new File("./abc/"), suffixList, dirFilterList);
        for (File tempFile : fileList) {
            System.out.println(tempFile.getAbsolutePath() + "," + tempFile.getName());
        }
    }

    @Test
    public void testGetListFilesInDirWithFilter2(){
        String[] suffixArray = new String[]{"txt", "docx"};
        List<String> dirFilterList = null;
//        dirFilterList.add("d");
//        dirFilterList.add("e");
        List<File> fileList = FileUtils.listFilesInDirWithFilter(new File("./abc/x"), suffixArray, dirFilterList);
        if(fileList == null || fileList.size() == 0) {
            return;
        }
        for (File tempFile : fileList) {
            System.out.println(tempFile.getAbsolutePath() + "," + tempFile.getName());
        }
    }
}
