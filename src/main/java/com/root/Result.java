package com.root;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
class Result {
    private BigInteger fileCount = BigInteger.ZERO;
    private BigInteger folderCount = BigInteger.valueOf(-1);
    private BigInteger filesSize = BigInteger.ZERO;

    public void addFileCount(long fileCount) {
        this.fileCount = this.fileCount.add(BigInteger.valueOf(fileCount));
    }

    public void addFolderCount(long folderCount) {
        this.folderCount = this.folderCount.add(BigInteger.valueOf(folderCount));
    }

    public void addFileSize(long filesSize) {
        this.filesSize = this.filesSize.add(BigInteger.valueOf(filesSize));
    }

    public void mergeResults(Result from) {
        addFileSize(from.getFilesSize().longValue());
        addFolderCount(from.getFolderCount().longValue());
        addFileCount(from.getFileCount().longValue());
    }
}
