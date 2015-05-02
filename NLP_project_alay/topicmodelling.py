import logging
logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)


from gensim import corpora, models, similarities 
import nltk

stoplist = set('for a of the and to in as is'.split())
texts = [[word for word in document.lower().split() if word not in stoplist]
         for document in open('trainingdata.txt')]

# remove words that appear only once
from collections import defaultdict

frequency = defaultdict(int)

for text in texts:
    for token in text:
        frequency[token] += 1

texts = [[token for token in text if frequency[token] > 1]
         for text in texts]

tagged = [ nltk.pos_tag(text) for text in texts ]
texts = [[word[0] for word in tagged_sent if word[1][0] == "N" and word[1][1] == "N"] for tagged_sent in tagged]
texts = [l1 for l1 in texts if l1]



dictionary = corpora.Dictionary(texts)
dictionary.save('/tmp/deerwester.dict')

corpus = [dictionary.doc2bow(text) for text in texts]
corpora.MmCorpus.serialize('/tmp/deerwester.mm', corpus)


dictionary = corpora.Dictionary.load('/tmp/deerwester.dict')
corpus = corpora.MmCorpus('/tmp/deerwester.mm')

tfidf = models.TfidfModel(corpus)
corpus_tfidf = tfidf[corpus]

lda = models.LdaModel(corpus_tfidf, id2word=dictionary, num_topics=5)
corpus_lda = lda[corpus_tfidf]

lda.print_topic(1)
for doc in corpus_lda: # both bow->tfidf and tfidf->lsi transformations are actually executed here, on the fly
     print(doc[1][1])