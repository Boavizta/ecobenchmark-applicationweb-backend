import sys
import os
import json
import csv

def is_run_valid(summary):
    if summary['metrics']['vus_max']['value'] != 20:
        return False
    for name in summary['root_group']['checks']:
        check = summary['root_group']['checks'][name]
        if check['fails'] > 0 and check['passes'] == 0:
            return False
    if 'iterations' not in summary['metrics']:
        return False
    return True

def avg(data):
    return sum(data) / len(data)

def median(data):
    data.sort()
    mid = len(data) // 2
    return (data[mid] + data[~mid]) / 2

def merge(target, values):
    for (key, value) in values.items():
        target[key] = value

def merge_with_prefix(target, prefix, values):
    for (old_key, value) in values.items():
        new_key = prefix + "_" + old_key
        target[new_key] = value

def parse_docker_activity(path, container_name = []):
    cpus = []
    rams = []
    cpu_count = 1
    for data in read_jsonp(path):
        if data['containerName'] == container_name and data['cpuCount'] and data['cpuPercent'] and data['memoryUsage']:
            cpu_count = data['cpuCount']
            cpus.append(data['cpuPercent'])
            rams.append(data['memoryUsage'])
    return {
        # 'ram_min': min(rams),
        # 'ram_max': max(rams),
        'ram_avg': avg(rams),
        'ram_median': median(rams),
        # 'cpu_min': min(cpus),
        # 'cpu_max': max(cpus),
        'cpu_avg': avg(cpus),
        'cpu_median': median(cpus),
        # 'cpu_count': cpu_count
    }

def parse_scaphandre(path, execs):
    data = read_json(path)
    # consumers = []
    consumptions = []
    for item in data:
        for consumer in item['consumers']:
            # if consumer['exe'] not in consumers:
            #     consumers.append(consumer['exe'])
            if consumer['exe'] in execs:
                consumptions.append(consumer['consumption'])
    # consumers.sort()
    # print(consumers)
    return sum(consumptions)

def parse_summary(path):
    summary = read_json(path)
    valid = is_run_valid(summary)
    return {
        'valid': valid,
        'vus': summary['metrics']['vus_max']['value'],
        'iterations': summary['metrics']['iterations']['count'],
        'http_reqs': summary['metrics']['http_reqs']['count'],
        'checks_passes': summary['metrics']['checks']['passes'],
        'checks_fails': summary['metrics']['checks']['fails'],
    }

def read_folder(path):
    results = dict()
    for entry in os.scandir(path):
        if entry.name.endswith('.json') and len(entry.name.split('_')) == 4:
            [service, use_case, run_id, ftype] = entry.name[:-5].split('_')
            if not service in results:
                results[service] = dict()
            if not use_case in results[service]:
                results[service][use_case] = dict()
            if not run_id in results[service][use_case]:
                results[service][use_case][run_id] = dict()
            results[service][use_case][run_id][ftype] = entry.path
    output = []
    for (service, use_cases) in with_sorted_items(results):
        for (use_case, runs) in with_sorted_items(use_cases):
            for (run_id, files) in with_sorted_items(runs):
                if 'k6-summary' in files and 'scaphandre-application' in files and 'scaphandre-database' in files and 'docker-activity-application' in files and 'docker-activity-database' in files:
                    output.append((service, use_case, run_id, files))
                else:
                    print("skipping", service, use_case, run_id)
    return output

def read_json(path):
    with open(path) as f:
        return json.load(f)

def read_jsonp(path):
    with open(path) as f:
        data = []
        for line in f:
            try:
                data.append(json.loads(line))
            except Exception as err:
                print("shit happened with line", line)
                print(err)
        return data

def with_sorted_items(input):
    keys = [ id for id in input.keys() ]
    keys.sort()
    return [ (id, input[id]) for id in keys ]

result = []
headers = None
for (service, use_case, run_id, files) in read_folder(sys.argv[1]):
    try:
        summary = parse_summary(files['k6-summary'])
        entry = {
            'service': service,
            'use_case': use_case,
            'run_id': run_id,
        }
        merge(entry, parse_summary(files['k6-summary']))
        merge_with_prefix(entry, 'application', parse_docker_activity(files['docker-activity-application'], "eco-benchmark-application"))
        entry['application_energy'] = parse_scaphandre(files['scaphandre-application'], ['app', 'apache2', 'java', 'node', 'rust-actix-sqlx'])
        merge_with_prefix(entry, 'database', parse_docker_activity(files['docker-activity-database'], "eco-benchmark-database"))
        entry['database_energy'] = parse_scaphandre(files['scaphandre-database'], ['postgres', 'mysqld'])

        if entry['valid']:
            result.append(entry)
            headers = entry.keys()
        
        print("done with", service, use_case, run_id)

    except Exception as err:
        print("shit happened with", service, use_case, run_id)
        print(err)

if headers:
    with open('result.csv', 'w', encoding='UTF8') as f:
        writer = csv.DictWriter(f, fieldnames=headers)
        writer.writeheader()
        writer.writerows(result)
else:
    print("no headers found")
