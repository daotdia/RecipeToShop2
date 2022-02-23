import json
from flask import Flask, request
from flask_rest_jsonapi import Api, ResourceDetail, ResourceList
from carrefour.main_carrefour import main_Carrefour as cf


#Crea la App Flask.
app = Flask(__name__)        

@app.route('/query')
def query_example():
    # if key doesn't exist, returns None
    query = json.dumps(request.args.get(query))
    response = cf.execQuery(query)
    return '''{}'''.format(response)

if __name__ == '__main__':
    app.run(debug=True, port=8080)