using System;
using System.Collections;
using System.IO;
using System.Net;
using System.Security.Cryptography;
using System.Text;
using System.Web;

namespace dianping_.net_samples
{
    class Program
    {
      
        static void Main(string[] args)
        {
            //请替换AppKey以及Secret
            string appKey = "XXXXXXXX";
            string secret = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
            string value = "";
            string queryString = "";

            //准备参数
            Hashtable ht = new Hashtable();
            ht.Add("format", "json");
            ht.Add("city", "北京");
            ht.Add("region", "海淀区");
            ht.Add("category", "火锅");
            ht.Add("has_coupon", "1");
            ht.Add("sort", "2");
            ht.Add("limit", "1");

            //参数按照参数名排序
            ArrayList akeys = new ArrayList(ht.Keys);
            akeys.Sort();

            //拼接字符串
            foreach (string skey in akeys)
            {
                value += skey + ht[skey].ToString();
                queryString += "&" + skey + "=" + Utf8Encode(ht[skey].ToString());
            }
            StringBuilder sb = new StringBuilder();
            sb.Append(appKey);
            sb.Append(value);
            sb.Append(secret);
            value = sb.ToString();

            string url = "http://api.dianping.com/v1/business/find_businesses?appkey=" + appKey + "&sign=" + SHA1(value) + queryString;
            int status = 0;
           
            Console.WriteLine(url);
            Console.WriteLine(RequestUrl(url, out status));
            Console.ReadLine();
        }

        /// <summary>
        /// URL请求
        /// </summary>
        /// <param name="url">URL地址</param>
        /// <param name="status">URL请求响应状态码</param>
        /// <returns>请求结果</returns> 
        public static string RequestUrl(string url, out int status)
        {
            string result = null;
            status = 0;
            HttpWebResponse response = null;
            try
            {
                HttpWebRequest request = (HttpWebRequest)HttpWebRequest.Create(url);
                response = (HttpWebResponse)request.GetResponse();
                Encoding responseEncoding = Encoding.GetEncoding(response.CharacterSet);
                using (StreamReader sr = new StreamReader(response.GetResponseStream(), responseEncoding))
                {
                    result = sr.ReadToEnd();
                }
                status = (int)response.StatusCode;
            }
            catch (WebException wexc1)
            {
                // any statusCode other than 200 gets caught here
                if (wexc1.Status == WebExceptionStatus.ProtocolError)
                {
                    // can also get the decription: 
                    //  ((HttpWebResponse)wexc1.Response).StatusDescription;
                    status = (int)((HttpWebResponse)wexc1.Response).StatusCode;
                }
            }
            finally
            {
                if (response != null)
                    response.Close();
            }
            return result;
        }

        /// <summary>
        /// URL请求参数UTF8编码
        /// </summary>
        /// <param name="value">源字符串</param>
        /// <returns>编码后的字符串</returns> 
        private static string Utf8Encode(string value)
        {
            return HttpUtility.UrlEncode(value, System.Text.Encoding.UTF8);
        }

        /// <summary>
        /// SHA1加密字符串
        /// </summary>
        /// <param name="source">源字符串</param>
        /// <returns>加密后的字符串</returns> 
        public static string SHA1(string source)
        {
            byte[] value = Encoding.UTF8.GetBytes(source);
           SHA1 sha = new SHA1CryptoServiceProvider();
           byte[] result = sha.ComputeHash(value);

           string delimitedHexHash = BitConverter.ToString(result);
           string hexHash = delimitedHexHash.Replace("-", "");

           return hexHash;
        }
    }

}
