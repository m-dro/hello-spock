class WordDetector {
    int wordsFound

    def parseText(text) {
        wordsFound = text.collect { it.charAt(0).digit || it.charAt(0).letter ? it : ' ' }
                .join('').tokenize(' ').size()
    }
}
