package com.tsinjo.demo.file.hash;

import com.tsinjo.demo.PojaGenerated;

@PojaGenerated
public record FileHash(FileHashAlgorithm algorithm, String value) {}
