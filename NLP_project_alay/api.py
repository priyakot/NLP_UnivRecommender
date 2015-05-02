import requests


headers={"X-Mashape-Key": "FQfku1gT4FmshdIPzQvTqYMjCsaZp1aMlw5jsnAEKdXZ8bANsV","Content-Type": "application/x-www-form-urlencoded","Accept": "application/json"}
params={"dataurl": "http://viterbi.usc.edu/news/news/2015/felipe-de-barros-mitigate-environmental-hazards-chemicals-leaching-into-soil-water-table.htm"}

r = requests.get("https://extracttext.p.mashape.com/api/content_extract/", headers=headers, params = params)
print(r.text)
