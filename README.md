## CodeSnippetRestApi
A java-based REST API, which creates a code-snippet with following functionalities: 

1. Provides option for creating code snippets of multiple languages such as Java, Python, Javascript and etc.

2. Lists the created code snippets (excluding private code snippets). The endpoint for viewing list is **"/CodeSnippetsRestApi/ShowSnippet/getJson/showList"**

3. To view the private code snippets there is a specific endpoint, which is: **"/CodeSnippetsRestApi/ShowSnippet/getJson/{Id}"**.

4. Filters the list given the language or date, the endpoint for this is: **"/CodeSnippetsRestApi/ShowSnippet/getJson/filter/{filter}"**.

5. Deletes the snippet given the secret, the endpoint for this is: **"/CodeSnippetsRestApi/ShowSnippet/getJson/delete/{secret}"**.

6. Search for keyword in the title or content of snippet, endpoint: **"/CodeSnippetsRestApi/ShowSnippet/getJson/search/{keyword}"**

7. Select the list size, endpoint: **"/CodeSnippetsRestApi/ShowSnippet/getJson/paginate/{pageSize}"**
